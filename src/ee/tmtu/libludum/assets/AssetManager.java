package ee.tmtu.libludum.assets;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {

	private static final Map<Class<?>, AssetLoader> assetLoaders = new HashMap<>();
	private static final Map<String, Object> loadedCache = new HashMap<>();

    public static final Gson gson = new Gson();

	public static <T> T load(String res, Class<T> type) {
		return load(res, type, assetLoaders);
	}

	public static <T> T load(String res, Class<T> type, Map<Class<?>, AssetLoader> loaders) {
		File file = new File(res);
        if (loadedCache.containsKey(file.getPath())) {
            return (T) loadedCache.get(file.getPath());
        }
		AssetLoader loader = loaders.get(type);
        T ret = null;
        try {
            ret = (T) loader.load(file);
            loadedCache.put(file.getPath(), ret);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
	}

	public static <T> void register(Class<?> c, AssetLoader<T> al) {
		assetLoaders.put(c, al);
	}

}