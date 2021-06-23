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
        try{
            String link = String.format("https://br.freepik.com/search?dates=any&format=search&page=1&query=%s&selection=1&sort=popular", search.replace(" ", "%20"));
            Document doc = Jsoup.connect(link).get();
            Elements noscript = doc.select("noscript");
            Elements img = noscript.select("img.landscape");
            Integer count = 0;
            for(String src : img.eachAttr("src")){
                String nameImage = src.split("/")[4];
                if(nameImage!="1px.png"){
                    count++;
                    URL urlConnection = new URL(src);
                    InputStream in = new BufferedInputStream(urlConnection.openStream());
                    OutputStream out = new BufferedOutputStream(new FileOutputStream("static/images/"+String.format("%s_%d", search.replace(" ", "_"), count)+".jpg"));
                    for ( int i; (i = in.read()) != -1; ){
                        out.write(i);
                    }
                    in.close();
                    out.close();
                }
            }
        }catch(Exception e){

        }
    }
}
