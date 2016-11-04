package com.devfactory.codeserver.api.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devfactory.codeserver.api.dto.RevisionDTO;
import com.devfactory.codeserver.api.dto.RevisionDTO.RevisionDTOOutput;
import com.devfactory.codeserver.api.exception.ApiException;
import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.service.RepositoryService;
import com.devfactory.codeserver.api.service.RevisionService;
import com.devfactory.codeserver.api.service.SourceApiService;

import lombok.AllArgsConstructor;

/**
 * Class to expose REST services to consume revisions.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping(
        value = "/repositories/{repoId}/revisions",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class RevisionController {

    private final RevisionService service;

    private final RepositoryService repoService;

    private final SourceApiService sourceApiService;

    private final ModelMapper mapper;

    @GetMapping
    public List<RevisionDTOOutput> findAll(@PathVariable Integer repoId) throws Exception {
        return service
                .findAll(repoId)
                .stream()
                .map((p) -> mapper.map(p, RevisionDTO.RevisionDTOOutput.class))
                .collect(Collectors.toList());
    }

    @GetMapping(
            value = "{rev}/codedb.zip/",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<InputStreamResource> downloadCodedb(
            @PathVariable Integer repoId,
            @PathVariable String rev) throws IOException, ApiException {
        Revision revision = service.findRevisionByRepositoryIdAndScmRevId(repoId, rev);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename=\"codedb.zip\"");

        Path outPath = sourceApiService.downloadCodedb(revision);
        outPath.toFile().deleteOnExit();

        InputStreamResource isr = new InputStreamResource(Files.newInputStream(outPath));

        return ResponseEntity
                .ok()
                .contentLength(Files.size(outPath))
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(isr);
    }


    @GetMapping("{revId}")
    public Revision find(
            @PathVariable Integer repoId,
            @PathVariable String revId) throws Exception {
        return service.findRevisionByRepositoryIdAndScmRevId(repoId, revId);
    }
}
