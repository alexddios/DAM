package engine.utils.images;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;


/**
 * Handles registration, loading, and retrieval of image resources located under
 * a given assets directory. Each image is identified by the hash of its URI and
 * stored as an ImageDTO instance. The class supports lazy loading, batched
 * manifest loading, random image selection, and access to both the DTO wrapper
 * and the underlying BufferedImage. All images are kept in memory for fast
 * lookup through a concurrent map.
 */
public class Images {

    private final String assetsPath;
    private final Map<String, ImageDTO> images = new ConcurrentHashMap<>(128);
    private final Random rnd = new Random();


    /**
     * CONSTRUCTORS
     */
    public Images(String assetsPath) {
        this.assetsPath = assetsPath;
    }


    /**
     * PUBLIC
     */
    public void add(String assetId, String fileName) {
        if (fileName == null || fileName.isEmpty() || assetId == null || assetId.isEmpty()) {
            throw new IllegalArgumentException("Images: Image ID or filename cannot be null or empty");
        }

        this.images.put(assetId, this.loadImage(assetId, assetsPath + fileName));
    }


    public ArrayList<String> getAssetIds() {
        return new ArrayList(this.images.keySet());
    }


    public ImageDTO getImage(String assetId) {
        return this.images.get(assetId);
    }


    public int getSize() {
        return this.images.size();
    }


    public ImageDTO getRamdomImageDTO() {
        return this.choice();
    }


    public BufferedImage getRamdomBufferedImage() {
        return this.choice().image;
    }


    /**
     * PRIVATE
     */
    private ImageDTO choice() {
        String assetId = this.randomAssetId();

        return this.images.get(assetId);
    }


    private ImageDTO loadImage(String assetId, String uri) {
        ImageDTO imageDto = null;
        BufferedImage image;

        try {
            image = ImageIO.read(new File(uri));
            if (image == null) {
                throw new IOException("Images: Unsupported or empty image: " + uri);
            }
            imageDto = new ImageDTO(assetId, uri, image);

        } catch (IOException e) {
            throw new RuntimeException("Images: Failed to load image: " + uri, e);
        }

        return imageDto;
    }


    public static BufferedImage loadBufferedImage(String path, String fileName) {
        File uri = Paths.get(path, fileName).toFile();
//        String uri = path +fileName;

        try {
//            BufferedImage image = ImageIO.read(new File(uri));
            BufferedImage image = ImageIO.read(uri);

            if (image == null) {
                throw new IOException("Images: Unsupported or empty image: " + uri);
            }

            return image;

        } catch (IOException e) {
            throw new RuntimeException("Images: Failed to load image: " + uri, e);
        }
    }


    public String randomAssetId() {
        List<String> keys = new ArrayList<>(this.images.keySet());

        if (keys.isEmpty()) {
            throw new IllegalStateException("No images loaded · Images");
        }
        int index = this.rnd.nextInt(keys.size());
        return keys.get(index);
    }
}
