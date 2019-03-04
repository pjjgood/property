package com.property.test.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.property.test.domain.PropertyMoney;
import com.property.test.service.PropertyMoneyService;
import com.property.test.web.rest.errors.BadRequestAlertException;
import com.property.test.web.rest.util.HeaderUtil;
import com.property.test.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PropertyMoney.
 */
@RestController
@RequestMapping("/api")
public class PropertyMoneyResource {

    private final Logger log = LoggerFactory.getLogger(PropertyMoneyResource.class);

    private static final String ENTITY_NAME = "propertyMoney";

    private PropertyMoneyService propertyMoneyService;

    public PropertyMoneyResource(PropertyMoneyService propertyMoneyService) {
        this.propertyMoneyService = propertyMoneyService;
    }

    /**
     * POST  /property-monies : Create a new propertyMoney.
     *
     * @param propertyMoney the propertyMoney to create
     * @return the ResponseEntity with status 201 (Created) and with body the new propertyMoney, or with status 400 (Bad Request) if the propertyMoney has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/property-monies")
    @Timed
    public ResponseEntity<PropertyMoney> createPropertyMoney(@Valid @RequestBody PropertyMoney propertyMoney) throws URISyntaxException {
        log.debug("REST request to save PropertyMoney : {}", propertyMoney);
        if (propertyMoney.getId() != null) {
            throw new BadRequestAlertException("A new propertyMoney cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropertyMoney result = propertyMoneyService.save(propertyMoney);
        return ResponseEntity.created(new URI("/api/property-monies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /property-monies : Updates an existing propertyMoney.
     *
     * @param propertyMoney the propertyMoney to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated propertyMoney,
     * or with status 400 (Bad Request) if the propertyMoney is not valid,
     * or with status 500 (Internal Server Error) if the propertyMoney couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/property-monies")
    @Timed
    public ResponseEntity<PropertyMoney> updatePropertyMoney(@Valid @RequestBody PropertyMoney propertyMoney) throws URISyntaxException {
        log.debug("REST request to update PropertyMoney : {}", propertyMoney);
        if (propertyMoney.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropertyMoney result = propertyMoneyService.save(propertyMoney);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, propertyMoney.getId().toString()))
            .body(result);
    }

    /**
     * GET  /property-monies : get all the propertyMonies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of propertyMonies in body
     */
    @GetMapping("/property-monies")
    @Timed
    public ResponseEntity<List<PropertyMoney>> getAllPropertyMonies(Pageable pageable) {
        log.debug("REST request to get a page of PropertyMonies");
        Page<PropertyMoney> page = propertyMoneyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/property-monies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /property-monies/:id : get the "id" propertyMoney.
     *
     * @param id the id of the propertyMoney to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the propertyMoney, or with status 404 (Not Found)
     */
    @GetMapping("/property-monies/{id}")
    @Timed
    public ResponseEntity<PropertyMoney> getPropertyMoney(@PathVariable Long id) {
        log.debug("REST request to get PropertyMoney : {}", id);
        Optional<PropertyMoney> propertyMoney = propertyMoneyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propertyMoney);
    }

    /**
     * DELETE  /property-monies/:id : delete the "id" propertyMoney.
     *
     * @param id the id of the propertyMoney to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/property-monies/{id}")
    @Timed
    public ResponseEntity<Void> deletePropertyMoney(@PathVariable Long id) {
        log.debug("REST request to delete PropertyMoney : {}", id);
        propertyMoneyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}