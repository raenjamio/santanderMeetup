package com.raenjamio.web.rest;

import com.raenjamio.MeetupApp;
import com.raenjamio.domain.Meetup;
import com.raenjamio.repository.MeetupRepository;
import com.raenjamio.service.MeetupService;
import com.raenjamio.service.dto.MeetupDTO;
import com.raenjamio.service.mapper.MeetupMapper;
import com.raenjamio.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.raenjamio.web.rest.TestUtil.sameInstant;
import static com.raenjamio.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MeetupResource} REST controller.
 */
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class MeetupResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_MEETUP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MEETUP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LOCATION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMBER_OF_PEOPLE_CONFIRMED = 1L;
    private static final Long UPDATED_NUMBER_OF_PEOPLE_CONFIRMED = 2L;

    @Autowired
    private MeetupRepository meetupRepository;

    @Mock
    private MeetupRepository meetupRepositoryMock;

    @Autowired
    private MeetupMapper meetupMapper;

    @Mock
    private MeetupService meetupServiceMock;

    @Autowired
    private MeetupService meetupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restMeetupMockMvc;

    private Meetup meetup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeetupResource meetupResource = new MeetupResource(meetupService);
        this.restMeetupMockMvc = MockMvcBuilders.standaloneSetup(meetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meetup createEntity() {
        Meetup meetup = new Meetup()
            .description(DEFAULT_DESCRIPTION)
            .dateMeetup(DEFAULT_DATE_MEETUP)
            .locationDescription(DEFAULT_LOCATION_DESCRIPTION)
            .numberOfPeopleConfirmed(DEFAULT_NUMBER_OF_PEOPLE_CONFIRMED);
        return meetup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meetup createUpdatedEntity() {
        Meetup meetup = new Meetup()
            .description(UPDATED_DESCRIPTION)
            .dateMeetup(UPDATED_DATE_MEETUP)
            .locationDescription(UPDATED_LOCATION_DESCRIPTION)
            .numberOfPeopleConfirmed(UPDATED_NUMBER_OF_PEOPLE_CONFIRMED);
        return meetup;
    }

    @BeforeEach
    public void initTest() {
        meetupRepository.deleteAll();
        meetup = createEntity();
    }

    @Test
    public void createMeetup() throws Exception {
        int databaseSizeBeforeCreate = meetupRepository.findAll().size();

        // Create the Meetup
        MeetupDTO meetupDTO = meetupMapper.toDto(meetup);
        restMeetupMockMvc.perform(post("/api/meetups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetupDTO)))
            .andExpect(status().isCreated());

        // Validate the Meetup in the database
        List<Meetup> meetupList = meetupRepository.findAll();
        assertThat(meetupList).hasSize(databaseSizeBeforeCreate + 1);
        Meetup testMeetup = meetupList.get(meetupList.size() - 1);
        assertThat(testMeetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMeetup.getDateMeetup()).isEqualTo(DEFAULT_DATE_MEETUP);
        assertThat(testMeetup.getLocationDescription()).isEqualTo(DEFAULT_LOCATION_DESCRIPTION);
        assertThat(testMeetup.getNumberOfPeopleConfirmed()).isEqualTo(DEFAULT_NUMBER_OF_PEOPLE_CONFIRMED);
    }

    @Test
    public void createMeetupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meetupRepository.findAll().size();

        // Create the Meetup with an existing ID
        meetup.setId("existing_id");
        MeetupDTO meetupDTO = meetupMapper.toDto(meetup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeetupMockMvc.perform(post("/api/meetups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meetup in the database
        List<Meetup> meetupList = meetupRepository.findAll();
        assertThat(meetupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = meetupRepository.findAll().size();
        // set the field null
        meetup.setDescription(null);

        // Create the Meetup, which fails.
        MeetupDTO meetupDTO = meetupMapper.toDto(meetup);

        restMeetupMockMvc.perform(post("/api/meetups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetupDTO)))
            .andExpect(status().isBadRequest());

        List<Meetup> meetupList = meetupRepository.findAll();
        assertThat(meetupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateMeetupIsRequired() throws Exception {
        int databaseSizeBeforeTest = meetupRepository.findAll().size();
        // set the field null
        meetup.setDateMeetup(null);

        // Create the Meetup, which fails.
        MeetupDTO meetupDTO = meetupMapper.toDto(meetup);

        restMeetupMockMvc.perform(post("/api/meetups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetupDTO)))
            .andExpect(status().isBadRequest());

        List<Meetup> meetupList = meetupRepository.findAll();
        assertThat(meetupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllMeetups() throws Exception {
        // Initialize the database
        meetupRepository.save(meetup);

        // Get all the meetupList
        restMeetupMockMvc.perform(get("/api/meetups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meetup.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateMeetup").value(hasItem(sameInstant(DEFAULT_DATE_MEETUP))))
            .andExpect(jsonPath("$.[*].locationDescription").value(hasItem(DEFAULT_LOCATION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].numberOfPeopleConfirmed").value(hasItem(DEFAULT_NUMBER_OF_PEOPLE_CONFIRMED.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMeetupsWithEagerRelationshipsIsEnabled() throws Exception {
        MeetupResource meetupResource = new MeetupResource(meetupServiceMock);
        when(meetupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMeetupMockMvc = MockMvcBuilders.standaloneSetup(meetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMeetupMockMvc.perform(get("/api/meetups?eagerload=true"))
        .andExpect(status().isOk());

        verify(meetupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMeetupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        MeetupResource meetupResource = new MeetupResource(meetupServiceMock);
            when(meetupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMeetupMockMvc = MockMvcBuilders.standaloneSetup(meetupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMeetupMockMvc.perform(get("/api/meetups?eagerload=true"))
        .andExpect(status().isOk());

            verify(meetupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getMeetup() throws Exception {
        // Initialize the database
        meetupRepository.save(meetup);

        // Get the meetup
        restMeetupMockMvc.perform(get("/api/meetups/{id}", meetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meetup.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateMeetup").value(sameInstant(DEFAULT_DATE_MEETUP)))
            .andExpect(jsonPath("$.locationDescription").value(DEFAULT_LOCATION_DESCRIPTION))
            .andExpect(jsonPath("$.numberOfPeopleConfirmed").value(DEFAULT_NUMBER_OF_PEOPLE_CONFIRMED.intValue()));
    }

    @Test
    public void getNonExistingMeetup() throws Exception {
        // Get the meetup
        restMeetupMockMvc.perform(get("/api/meetups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMeetup() throws Exception {
        // Initialize the database
        meetupRepository.save(meetup);

        int databaseSizeBeforeUpdate = meetupRepository.findAll().size();

        // Update the meetup
        Meetup updatedMeetup = meetupRepository.findById(meetup.getId()).get();
        updatedMeetup
            .description(UPDATED_DESCRIPTION)
            .dateMeetup(UPDATED_DATE_MEETUP)
            .locationDescription(UPDATED_LOCATION_DESCRIPTION)
            .numberOfPeopleConfirmed(UPDATED_NUMBER_OF_PEOPLE_CONFIRMED);
        MeetupDTO meetupDTO = meetupMapper.toDto(updatedMeetup);

        restMeetupMockMvc.perform(put("/api/meetups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetupDTO)))
            .andExpect(status().isOk());

        // Validate the Meetup in the database
        List<Meetup> meetupList = meetupRepository.findAll();
        assertThat(meetupList).hasSize(databaseSizeBeforeUpdate);
        Meetup testMeetup = meetupList.get(meetupList.size() - 1);
        assertThat(testMeetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMeetup.getDateMeetup()).isEqualTo(UPDATED_DATE_MEETUP);
        assertThat(testMeetup.getLocationDescription()).isEqualTo(UPDATED_LOCATION_DESCRIPTION);
        assertThat(testMeetup.getNumberOfPeopleConfirmed()).isEqualTo(UPDATED_NUMBER_OF_PEOPLE_CONFIRMED);
    }

    @Test
    public void updateNonExistingMeetup() throws Exception {
        int databaseSizeBeforeUpdate = meetupRepository.findAll().size();

        // Create the Meetup
        MeetupDTO meetupDTO = meetupMapper.toDto(meetup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeetupMockMvc.perform(put("/api/meetups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meetup in the database
        List<Meetup> meetupList = meetupRepository.findAll();
        assertThat(meetupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteMeetup() throws Exception {
        // Initialize the database
        meetupRepository.save(meetup);

        int databaseSizeBeforeDelete = meetupRepository.findAll().size();

        // Delete the meetup
        restMeetupMockMvc.perform(delete("/api/meetups/{id}", meetup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Meetup> meetupList = meetupRepository.findAll();
        assertThat(meetupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meetup.class);
        Meetup meetup1 = new Meetup();
        meetup1.setId("id1");
        Meetup meetup2 = new Meetup();
        meetup2.setId(meetup1.getId());
        assertThat(meetup1).isEqualTo(meetup2);
        meetup2.setId("id2");
        assertThat(meetup1).isNotEqualTo(meetup2);
        meetup1.setId(null);
        assertThat(meetup1).isNotEqualTo(meetup2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeetupDTO.class);
        MeetupDTO meetupDTO1 = new MeetupDTO();
        meetupDTO1.setId("id1");
        MeetupDTO meetupDTO2 = new MeetupDTO();
        assertThat(meetupDTO1).isNotEqualTo(meetupDTO2);
        meetupDTO2.setId(meetupDTO1.getId());
        assertThat(meetupDTO1).isEqualTo(meetupDTO2);
        meetupDTO2.setId("id2");
        assertThat(meetupDTO1).isNotEqualTo(meetupDTO2);
        meetupDTO1.setId(null);
        assertThat(meetupDTO1).isNotEqualTo(meetupDTO2);
    }
}
