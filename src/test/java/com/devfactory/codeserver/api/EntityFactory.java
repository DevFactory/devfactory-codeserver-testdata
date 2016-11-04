package com.devfactory.codeserver.api;

import com.google.common.collect.ImmutableList;

import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.RepositoryStatus;
import com.devfactory.codeserver.api.model.Revision;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Class to provide utility objects.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public abstract class EntityFactory {

    public static Repository newRepository() {
        return newRepository("any", "any", "any");
    }

    public static Repository newRepository(String name, String fullUrl, String lang) {
        Repository r = new Repository();
        r.setName(name);
        r.setScmUrl(fullUrl);
        r.setShortUrl(fullUrl);
        r.setBranch("master");
        r.setStartDate(new Date());
        r.setLanguages(Collections.singletonMap(lang, 1d));
        return r;
    }

    public static Repository newRepository(String name, String url, String branch, String lang) {
        Repository r = new Repository();
        r.setId(RandomUtils.nextInt(0, 1000));
        r.setName(name);
        r.setScmUrl(url);
        r.setShortUrl(url);
        r.setBranch(branch);
        r.setStartDate(new Date(0));
        r.setLanguages(Collections.singletonMap(lang, 1d));
        r.setStatus(RepositoryStatus.notInitialized());
        return r;
    }

    public static Revision newRevision() {
        return newRevision(1234, "any", "any");
    }

    public static Revision newRevision(Integer repoId, String scmRev, String author) {
        Revision r = new Revision();
        r.setScmRev(scmRev);
        r.setRepositoryId(repoId);
        r.setAuthor(author);
        return r;
    }

    public static Repository newLongMethodScmRepo() {
        return newRepository("tmp-longmethod", "https://github.com/rwanderc/tmp-longmethod.git", "master", "Java");
    }

    public static Repository newFailScmRepo() {
        return newRepository("tmp-longmethod", "https://asdasdasd.com/aggfbw/anyproject.git", "master", "Java");
    }

    public static Repository newMissingScmRepo() {
        return newRepository("tmp-longmethod", "https://github.com/rwanderc/skdjfnsdfyasdbgubdsuhgbsudfbghdsbghsgbdjgksdfbgjsdgjkf.git", "master", "Java");
    }

    public static Repository newSourceAPIScmRepo() {
        return newRepository("codeserver", "https://scm.devfactory.com/stash/scm/dfapi/source-api.git", "developer", "Java");
    }

    public static List<MultipartFile> getLongMethodMultipartFiles() throws IOException {
        return ImmutableList.of(new MockMultipartFile("file", "base_case.zip", "application/zip", new FileInputStream("./src/test/resources/files/longmethod/base_case.zip")));
    }

    public static List<File> getLongMethodFiles() throws IOException {
        return ImmutableList.of(new File("./src/test/resources/files/longmethod/base_case.zip"));
    }
}
