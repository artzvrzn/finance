package com.artzvrzn.view;

import com.artzvrzn.model.MailParams;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.UUID;

@Component
public class ReportCommunicator {

    @Value("${urls.report-service}")
    private String reportServiceUrl;

    private final RestTemplate restTemplate;

    public ReportCommunicator(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    public UUID postRequestReport(MailParams params) {
        String url = reportServiceUrl + "backend/report/" + params.getType().toUpperCase();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(params.getParams(), headers);
        try {
            return restTemplate.postForObject(url, entity, UUID.class);
        } catch (HttpStatusCodeException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public boolean isReportAvailable(UUID id) {
        String url = reportServiceUrl + "account/" + id + "/export";
        return restTemplate.headForHeaders(url).getContentLength() > -1;
    }

    public File getRequestReport(UUID id) {
        String url = reportServiceUrl + "account/" + id + "/export";
        return restTemplate.execute(url, HttpMethod.GET, null, clientHttpResponse -> {
            String filename = clientHttpResponse.getHeaders().getContentDisposition().getFilename();
            if (filename == null || filename.isEmpty()) {
                filename = id.toString() + ".tmp";
            }
            File ret = File.createTempFile(filename, getExtension(filename));
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            ret.deleteOnExit();
            return ret;
        });
    }

    private String getExtension(String filename) {
       return "." + FilenameUtils.getExtension(filename);
    }
}
