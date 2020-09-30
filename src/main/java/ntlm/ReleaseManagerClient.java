package ntlm;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

public class ReleaseManagerClient {
    public static void main(String[] args) throws IOException {

        final String username = args[0];
        final String password = args[1];
        final String workstation = args[2];
        final String domain = args[3];

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY,
                new NTCredentials(username, password, workstation, domain));

        RequestConfig config = RequestConfig.custom()
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM))
                .build();

        HttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(credsProvider)
                .build();

//        // Can be Done with HttpClientContext - looks like it has the same impact
//        HttpClientContext context = HttpClientContext.create();
//        context.setCredentialsProvider(credsProvider);

        HttpPost post = new HttpPost("https://releasemanager/api/release-note/main/get-release-note-data");
        post.setConfig(config);

        String bodyAsString = "{\n" +
                "  \"repositoryLocation\":\"Vantage\\\\VBW\\\\100.201.0\",\n" +
                "  \"componentLocation\":\"BLS_DC\",\n" +
                "  \"compType\":1\n" +
                "}";
        StringEntity input = new StringEntity(bodyAsString,Consts.UTF_8.name());
        input.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        input.setContentEncoding(Consts.UTF_8.name());
        post.setEntity(input);

        post.setHeader(
                HttpHeaders.ACCEPT,
                ContentType.APPLICATION_JSON.getMimeType()
        );
        post.setHeader(
                HttpHeaders.CONTENT_TYPE,
                ContentType.APPLICATION_JSON.getMimeType()
        );


        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream content = entity.getContent();
                // Can read the InputStream to String and use String.contains e.g -> contentAsString.contains(100.201.0.119)
                ObjectMapper om = new ObjectMapper();
                Map map = om.readValue(content, Map.class);
                System.out.println(map);

            }
        }

        System.out.println(response);

    }
}
