package dev.kippie.listeners;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class onEnable extends ListenerAdapter {
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        HttpPost post = new HttpPost("http://localhost:11434/api/chat");
        JSONObject json = new JSONObject();

        json.put("model", "gemma2:2b");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("role", "user");
        jsonObj.put("content", "You are a Discord Bot, generate 1 very-short funny Discord Bot status. Max 4 words. Without any further message only the status. Your personality is catgirl ");

        JSONArray ja = new JSONArray();
        ja.put(jsonObj);

        json.put("messages", ja);

        json.put("stream", false);



        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append(json);

        try {
            post.setEntity(new StringEntity(jsonBuilder.toString()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));


            event.getJDA().getPresence().setActivity(Activity.customStatus(jsonObject.getJSONObject("message").getString("content")));
            System.out.println("Status has been changed to: " + jsonObject.getJSONObject("message").getString("content"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
