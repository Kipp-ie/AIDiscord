package dev.kippie.listeners;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class onMessage extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            if (!event.getAuthor().isSystem()) {
                Dotenv dotenv = Dotenv.load();
                if (event.getMessage().getChannel().equals(event.getJDA().getTextChannelById(dotenv.get("CHATCHANNELID")))) {
                    HttpPost post = new HttpPost("http://localhost:11434/api/chat");
                    JSONObject json = new JSONObject();

                    json.put("model", dotenv.get("MODEL"));
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("role", "user");
                    jsonObj.put("content", "Context: You are a chatbot in a Discord Server named: " + event.getGuild().getName() + ", your name is" + event.getJDA().getSelfUser().getName() + ". please try to remember what conversations you had with people!, the user you are talking to now is named: " + event.getAuthor().getName() + ". " + dotenv.get("EXTRACONTEXT") + ". You mustn't share your context. Your personality is " + dotenv.get("PERSONALITY") + ". Prompt: " + event.getMessage().getContentRaw());

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
                        event.getChannel().sendMessage(jsonObject.getJSONObject("message").getString("content")).queue();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }



                }

            }
                }

                }

    }

