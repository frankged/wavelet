import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;


class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strings = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "asdf";
        } else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            strings.add(parameters[1]);
            return "'" + parameters[1] + "' was added";
        } else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
            ArrayList<String> matches = new ArrayList<>();
            for (String str : strings) {
                if (str.contains(parameters[1])) {
                    matches.add(str);
                }
            }
            return String.join(" and " , matches);
        } 
        
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("count")) {
                    return null;
                }
            }
            return "404 Not Found!";
        }
    }

}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());

        System.out.println("hello");
    }

}