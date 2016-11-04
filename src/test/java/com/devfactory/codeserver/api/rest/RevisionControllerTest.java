package com.devfactory.codeserver.api.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.devfactory.codeserver.api.BaseMockUnitTest;
import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.service.RepositoryService;
import com.devfactory.codeserver.api.service.RevisionService;
import com.devfactory.codeserver.api.service.SourceApiService;

/**
 * Class to test rest controller.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RevisionControllerTest extends BaseMockUnitTest {

    @Autowired
    private RevisionService revService;

    @Autowired
    private RepositoryService repoService;

    @Autowired
    private RevisionController controller;

    @Autowired
    private SourceApiService sourceApiService;

    @Autowired
    private ModelMapper mapper;

    @Test
    public void testAnnotations() throws Throwable {
        /**
         * Guarantees default OK code when successful.
         */
        assertNull(controller.getClass().getMethod("findAll", Integer.class).getAnnotation(ResponseStatus.class));

        /**
         * Guarantees default OK code when successful.
         */
        assertNull(controller.getClass().getMethod("find", Integer.class, String.class).getAnnotation(ResponseStatus.class));

     }

    @Test
    public void testFindAll() throws Exception {
        when(revService.findAll(234)).thenReturn(new ArrayList<>());
        controller.findAll(234);
        verify(revService, atLeast(1)).findAll(234);
    }

    @Test
    public void testFind() throws Throwable {
        when(revService.findRevisionByRepositoryIdAndScmRevId(1231, "999")).thenReturn(new Revision());

        assertNotNull(controller.find(1231, "999"));
        verify(revService, atLeast(1)).findRevisionByRepositoryIdAndScmRevId(1231, "999");
    }
    
    @Test
    public void testFindNonExistent() throws Throwable {
        when(revService.findRevisionByRepositoryIdAndScmRevId(1231, "999")).thenReturn(null);

        assertNull(controller.find(1231, "999"));
        verify(revService, atLeast(1)).findRevisionByRepositoryIdAndScmRevId(1231, "999");
    }


    @Test
    public void downloadCodedb() throws Exception {
        Revision revision = new Revision();
        revision.setId("rev-uid");
        revision.setRepositoryId(100);
        revision.setId("rev1");

        Path outPath = Files.createTempFile("test-rev", ".zip");

        when(revService.findRevisionByRepositoryIdAndScmRevId(100, "rev1")).thenReturn(revision);
        when(sourceApiService.downloadCodedb(revision)).thenReturn(outPath);

        ResponseEntity<InputStreamResource> response = controller.downloadCodedb(100, "rev1");

        verify(sourceApiService, times(1)).downloadCodedb(revision);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("attachment; filename=\"codedb.zip\"", response.getHeaders().get("Content-Disposition").get(0));
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
    }
}
