package com.devfactory.codeserver.api.rest;

import com.devfactory.codeserver.api.dto.RepositoryDTO;
import com.devfactory.codeserver.api.dto.RepositoryDTO.RepositoryDTOInputSave;
import com.devfactory.codeserver.api.dto.RepositoryDTO.RepositoryDTOInputUpdate;
import com.devfactory.codeserver.api.dto.RepositoryDTO.RepositoryDTOOutput;
import com.devfactory.codeserver.api.dto.RevisionDTO;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.RepositoryCheckoutStatus;
import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.service.RepositoryService;
import com.devfactory.codeserver.api.service.RevisionService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class to expose REST services to consume repositories.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(
        value = "/repositories",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class RepositoryController {

    private final RepositoryService service;

    private final RevisionService revisionService;

    private final ModelMapper mapper;

    @GetMapping
    public List<RepositoryDTOOutput> findAll() {
        return service
                .findAll()
                .stream()
                .map((p) -> mapToOutput(p))
                .collect(Collectors.toList());
    }

    private RepositoryDTOOutput mapToOutput(Repository repository) {
        RepositoryDTOOutput output = mapper.map(repository, RepositoryDTOOutput.class);
        String revId = repository.getHeadRevision();

        Revision revision = revisionService.findRevision(revId);

        if (revision != null) {
            RevisionDTO.RevisionDTOOutput revOutput = mapper.map(revision, RevisionDTO.RevisionDTOOutput.class);
            output.setHeadRevision(revOutput);
        }

        return output;
    }

    @GetMapping("{id}")
    public ResponseEntity<RepositoryDTOOutput> find(@PathVariable int id) {
        Repository repo = service.find(id);
        return repo != null && Objects.equals(repo.getStatus().getCheckoutStatus(), RepositoryCheckoutStatus.READY)
                ? new ResponseEntity<>(mapper.map(repo, RepositoryDTOOutput.class), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> save(@Valid @RequestBody RepositoryDTOInputSave dto) throws Exception {
        Repository repo = mapper.map(dto, Repository.class);
        repo.normalize(dto.getValidDate());
        Integer id = service.save(repo);
        HttpStatus status
                = Objects.equals(service.find(id).getStatus().getCheckoutStatus(), RepositoryCheckoutStatus.READY)
                ? HttpStatus.OK
                : HttpStatus.ACCEPTED;
        return new ResponseEntity<>(Collections.singletonMap("id", id), status);
    }

    @PutMapping(
            path = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(
            @PathVariable int id,
            @Valid @RequestBody RepositoryDTOInputUpdate dto) throws Exception {
        Repository repo = mapper.map(dto, Repository.class);
        service.update(id, repo);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() throws Exception {
        service.deleteAll();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) throws Exception {
        service.delete(id);
    }
}
