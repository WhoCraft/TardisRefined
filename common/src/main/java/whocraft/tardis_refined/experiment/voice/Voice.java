package whocraft.tardis_refined.experiment.voice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.Minecraft;
import org.vosk.Model;
import org.vosk.Recognizer;
import whocraft.tardis_refined.common.network.messages.voice.VoiceMessage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;

import static whocraft.tardis_refined.TardisRefined.GSON;

public class Voice {

    private static final String MODEL_PATH = "C:\\Users\\Craig\\Downloads\\vosk-model-small-en-us-0.15";
    private static Recognizer recognizer;
    private static TargetDataLine microphone;
    private static boolean running = true;

    public static void init() {
        try {
            Model model = new Model(MODEL_PATH);
            recognizer = new Recognizer(model, 16000.0f);
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            microphone = AudioSystem.getTargetDataLine(format);
            microphone.open();
            microphone.start();
            System.out.println("Microphone initialized.");

            // Start the recognition thread
            Thread recognitionThread = new Thread(Voice::recognize);
            recognitionThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void recognize() {
        while (running) {
            if (microphone != null && recognizer != null && Minecraft.getInstance().level != null) {
                try {
                    byte[] buffer = new byte[4096];
                    int numBytesRead;
                    ByteArrayOutputStream out = new ByteArrayOutputStream();

                    numBytesRead = microphone.read(buffer, 0, buffer.length);
                    out.write(buffer, 0, numBytesRead);
                    if (recognizer.acceptWaveForm(buffer, numBytesRead)) {
                        String result = VoiceMessages.extractTextFromJson(recognizer.getFinalResult());
                        System.out.println("Result: " + result);
                        if (!result.isBlank()) {
                            System.out.println("You said: " + result);
                            new VoiceMessage(result).send();
                        }
                    } else {
                        String result = VoiceMessages.extractTextFromJson(recognizer.getPartialResult());
                        System.out.println("Result: " + result);
                        if (result != null && !result.isBlank()) {
                            System.out.println("You said: " + result);
                            new VoiceMessage(result).send();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class VoiceMessages {
        private String text;

        // Constructor, getters, and setters omitted for brevity

        public static String extractTextFromJson(String jsonString) {
            Gson gson = new Gson();
            VoiceMessages message = gson.fromJson(jsonString, VoiceMessages.class);
            return message.getText();
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static void shutdown() {
        running = false;
        if (microphone != null) {
            microphone.close();
        }
        if (recognizer != null) {
            recognizer.close();
        }
    }
}
