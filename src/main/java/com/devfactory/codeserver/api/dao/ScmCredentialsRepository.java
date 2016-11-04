/**
 * @author yogesh.badke@aurea.com
 */
package com.devfactory.codeserver.api.dao;

import java.net.URL;
import java.util.Optional;

import com.devfactory.codeserver.api.CodeServerProperties.ScmCredential;

public interface ScmCredentialsRepository {
    Optional<ScmCredential> getScmCredentials(URL url);
}
