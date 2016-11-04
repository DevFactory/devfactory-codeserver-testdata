/**
 * @author yogesh.badke@aurea.com
 */
package com.devfactory.codeserver.api.dao.impl;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.CodeServerProperties.ScmCredential;
import com.devfactory.codeserver.api.dao.ScmCredentialsRepository;

@Repository
public class ScmCredentialsRepositoryImpl implements ScmCredentialsRepository {

    private final List<ScmCredential> scmCredentials;

    public ScmCredentialsRepositoryImpl(CodeServerProperties config) {
        scmCredentials = config.getScmConfigs();
    }

    @Override
    public Optional<ScmCredential> getScmCredentials(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("SCM Host can't be null");
        }
        return scmCredentials.stream()
                .filter(c -> startsWith(url, c.getHost())).findFirst();
    }

    private boolean startsWith(URL inputUrl, URL configuredHost) {
        String inputUrlString = inputUrl.toString();
        String configuredHostString = configuredHost.toString();
        return inputUrlString.length() < configuredHostString.length()
                ? configuredHostString.startsWith(inputUrlString)
                : inputUrlString.startsWith(configuredHostString);
    }
}
