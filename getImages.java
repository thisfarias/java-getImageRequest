import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;


public class getImages {
    public static void main(String[]args){
        String search = "Tudo roxo";
        Integer quantity = 5;
        try{
            String link = String.format("https://stock.adobe.com/br/search?k=%s&search_type=usertyped'", search.replace(" ", "+"));
            Document doc = Jsoup.connect(link).get();
            Elements metaLink = doc.select("meta[itemprop=thumbnailUrl]");
            Integer count = 0;
            for(String content : metaLink.eachAttr("content")){
                count++;
                System.out.println(content);
                URL urlConnection = new URL(content);
                InputStream in = new BufferedInputStream(urlConnection.openStream());
                OutputStream out = new BufferedOutputStream(new FileOutputStream("static/images/"+String.format("%s_%d", search.replace(" ", "_"), count)+".jpg"));
                for ( int i; (i = in.read()) != -1; ){
                    out.write(i);
                }
                in.close();
                out.close();
                if(count==quantity){
                    break;
                }
            }
        }catch(Exception e){

        }
    }
}