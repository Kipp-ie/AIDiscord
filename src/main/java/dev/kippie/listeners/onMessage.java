package dev.kippie.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class onMessage extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            if (!event.getAuthor().isSystem()) {
                HttpPost post = new HttpPost("http://localhost:11434/api/generate");

                // add request parameter, form parameters
                StringBuilder json = new StringBuilder();
                json.append("{");
                json.append("\"model\":\"gdisney/mistral-uncensored\",");
                json.append("\"prompt\":\"" + event.getMessage().getContentRaw() + "\",");
                json.append("\"stream\":false");
                json.append("}");
                System.out.println(event.getMessage().getContentRaw());

                try {
                    post.setEntity(new StringEntity(json.toString()));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }

                try (CloseableHttpClient httpClient = HttpClients.createDefault();
                     CloseableHttpResponse response = httpClient.execute(post)) {
                     JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
                     event.getChannel().sendMessage(jsonObject.getString("response")).queue();
                } catch (ClientProtocolException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



            }

        }
    }
}
