package com.raenjamio.useCase.beer.resource;

import com.raenjamio.security.AuthoritiesConstants;
import com.raenjamio.service.dto.BeerDTO;
import com.raenjamio.useCase.beer.service.BeerService;
import com.raenjamio.web.rest.FollowResource;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller for managing the count of beer for meetup
 */
@RestController
@RequestMapping("/api")
public class BeerResource {

    private final Logger log = LoggerFactory.getLogger(FollowResource.class);

    private final BeerService beerService;

    public BeerResource(BeerService beerService) {
        this.beerService = beerService;
    }

    /**
     * {@code GET  /meetups/:id/beers} : get the "id" meetup.
     *
     * @param id the id of the meetupDTO to calculate the beers.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meetupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meetups/{id}/beers")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<BeerDTO> getBeers(@PathVariable String id) {
        log.debug("REST request to get Meetup : {}", id);

        Optional<BeerDTO> beerDTO = beerService.countByMeetup(id);
        return ResponseUtil.wrapOrNotFound(beerDTO);
    }
}
