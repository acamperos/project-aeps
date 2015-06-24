package org.aepscolombia.platform.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HSOTELO
 */
public class RepositoryGoogle {
    public final static String geocoding_google_key ="Qcx8oSMdJjJzPgNHcy_91yErynY=";
    public final static String geocoding_google_client ="gme-centrointernacional";
    public final static String geocoding_google_url_send_xml ="https://maps.googleapis.com/maps/api/geocode/xml?sensor=false&";
    public final static String geocoding_google_url_send_json ="https://maps.googleapis.com/maps/api/geocode/json?sensor=true&";
    public final static String elevation_google_url_send_json ="https://maps.googleapis.com/maps/api/elevation/json?sensor=true&";
    public final static double geocoding_threshold =5000.0;
    
    private static HashMap db;
    private static Mac mac;
    
    /**
     * Create a string URL with the corresponding signature code and client id.
     * @param path
     * @param query
     * @return the specified String URL
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws URISyntaxException
     */
    private static String signRequest(String path, String query)throws NoSuchAlgorithmException, InvalidKeyException,UnsupportedEncodingException, URISyntaxException, IOException {
        if(RepositoryGoogle.mac==null){
            // key
            byte[] key= Base64.decode(RepositoryGoogle.geocoding_google_key.replace('-', '+').replace('_', '/'));
            // Get an HMAC-SHA1 signing key from the raw key bytes
            SecretKeySpec sha1Key = new SecretKeySpec(key, "HmacSHA1");
            // Get an HMAC-SHA1 Mac instance and initialize it with the HMAC-SHA1
            
            RepositoryGoogle.mac = Mac.getInstance("HmacSHA1");
            RepositoryGoogle.mac.init(sha1Key);
        }
        // Retrieve the proper URL components to sign
        String resource = path + '?' + query + "&client="+RepositoryGoogle.geocoding_google_client;        
        // compute the binary signature for the request
        byte[] sigBytes = RepositoryGoogle.mac.doFinal(resource.getBytes());
        // base 64 encode the binary signature
        String signature = Base64.encodeBytes(sigBytes);
        // convert the signature to 'web safe' base 64
        signature = signature.replace('+', '-').replace('/', '_');
        return resource + "&signature=" + signature;
    }
    
    /**
     * Method that geocoding a address from google api
     * @param country
     * @param adm1
     * @param adm2
     * @param adm3
     * @param local_area
     * @param locality
     * @return 
     */
    public static Location georenferencing(String country,String adm1, String adm2, String adm3,String local_area,String locality)
    {
        return RepositoryGoogle.georenferencing(country, adm1, adm2, adm3, local_area, locality, RepositoryGoogle.geocoding_threshold);
    }
    
    /**
     * Method that geocoding a address from google api
     * @param country
     * @param adm1
     * @param adm2
     * @param adm3
     * @param local_area
     * @param locality
     * @param uncertainty
     * @return 
     */
    public static Location georenferencing(String country,String adm1, String adm2, String adm3,String local_area,String locality,double uncertainty)
    {
        Location a=null;
        String key;
        try {
            key=RepositoryGoogle.generateKey(new String[]{country,adm1,adm2,adm3,local_area,locality});
            if(RepositoryGoogle.db==null)
                RepositoryGoogle.db=new HashMap();
            if(RepositoryGoogle.db.containsKey(key))
                return (Location)RepositoryGoogle.db.get(key);
            String data=(!country.equals("") ? country + "+,+" : "")+(!adm1.equals("") ? adm1 + "+,+" : "")+(!adm2.equals("") ? adm2 + "+,+" : "")+(!adm3.equals("") ? adm3 + "+,+" : "")+(!local_area.equals("") ? local_area + "+,+" : "")+(!locality.equals("") ? locality : "");
            URL url = new URL(RepositoryGoogle.geocoding_google_url_send_xml + "address=" + data.replace(" ","%20").replace(".","").replace(";",""));
            URL file_url = new URL(url.getProtocol() + "://" + url.getHost() + signRequest(url.getPath(), url.getQuery()));
            // Get information from URL
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // Create a proxy to work in CIAT (erase this in another place)
            //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy2.ciat.cgiar.org", 8080));
            DocumentBuilder db = dbf.newDocumentBuilder();
            //Document doc = db.parse(file_url.openConnection(proxy).getInputStream());
            Document doc = db.parse(file_url.openConnection().getInputStream());
            // Document with data
            if(doc !=null)
            {
                NodeList locationList = doc.getElementsByTagName("location");
                NodeList locationTypeList = doc.getElementsByTagName("location_type");
                NodeList viewPortList = doc.getElementsByTagName("viewport");
                Node location = null, lat = null, lng = null;
                if (locationList.getLength() > 0)
                {
                    for (int i = 0; i < locationList.getLength(); i++)
                    {
                        location = locationList.item(i);
                        if (location.hasChildNodes()) {
                            lat = location.getChildNodes().item(1);
                            lng = location.getChildNodes().item(3);
                        }
                    }
                    Node locationType = null;
                    if (locationTypeList.getLength() > 0)
                    {
                        for (int i = 0; i < locationTypeList.getLength(); i++)
                            locationType = locationTypeList.item(i);
                    }
                    Node viewPort = null, northeast = null, southwest = null, lat_northeast = null, lng_northeast = null, lat_southwest = null, lng_southwest = null;
                    if (viewPortList.getLength() > 0)
                    {
                        for (int i = 0; i < viewPortList.getLength(); i++)
                        {
                            viewPort = viewPortList.item(i);
                            if (viewPort.hasChildNodes())
                            {
                                northeast = viewPort.getChildNodes().item(1);
                                southwest = viewPort.getChildNodes().item(3);
                            }
                            /* Extract data from viewport field */
                            if (northeast.hasChildNodes())
                            {
                                lat_northeast = northeast.getChildNodes().item(1);
                                lng_northeast = northeast.getChildNodes().item(3);
                            }
                            if (southwest.hasChildNodes())
                            {
                                lat_southwest = southwest.getChildNodes().item(1);
                                lng_southwest = southwest.getChildNodes().item(3);
                            }
                        }
                    }
                    double[] coordValues = new double[]{Double.parseDouble(lat.getTextContent()), Double.parseDouble(lng.getTextContent())};
                    double[] coordValuesNortheast = new double[]{Double.parseDouble(lat_northeast.getTextContent()),Double.parseDouble(lng_northeast.getTextContent())};
                    double[] coordValuesSouthwest = new double[]{Double.parseDouble(lat_southwest.getTextContent()), Double.parseDouble(lng_southwest.getTextContent())} ;
                    double distance = RepositoryGoogle.getDistance(coordValuesNortheast, coordValuesSouthwest); 
                    // Distance - km between Northeast and Southeast                    
                    if (distance <= uncertainty) 
                        a=new Location(coordValues[0], coordValues[1], distance);
                    else
                    {
                        RepositoryGoogle.db.put(key, a);
                        throw new Exception("Exceede uncertainty. " + "Uncertainty: " + distance + " THRESHOLD: " + RepositoryGoogle.geocoding_threshold);
                    }
                        
                } 
            }            
            RepositoryGoogle.db.put(key, a);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        } catch (InvalidKeyException ex) {
            System.out.println(ex);
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ParserConfigurationException ex) {
            System.out.println(ex);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return a;
    }
    
    /**
     * 
     * @param latitude
     * @param longitude
     * @return 
     */
    public static HashMap reverse(double latitude, double longitude)
    {
        HashMap a=new HashMap();
        try
        {
            URL url = new URL(RepositoryGoogle.geocoding_google_url_send_json + "latlng=" + String.valueOf(latitude) + "," + String.valueOf(longitude));
            URL file_url = new URL(url.getProtocol() + "://" + url.getHost() + signRequest(url.getPath(), url.getQuery()));
            //BufferedReader lector=new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedReader lector=new BufferedReader(new InputStreamReader(file_url.openStream()));
            String textJson="",tempJs;
            while((tempJs=lector.readLine()) != null)
                textJson+=tempJs;
            if(textJson==null)
                throw new Exception("Don't found item" );
            JSONObject google=((JSONObject)JSONValue.parse(textJson));            
            a.put("status", google.get("status").toString());
            if(a.get("status").toString().equals("OK"))
            {
                JSONArray results=(JSONArray)google.get("results");
                System.out.println("results=>"+results);
                JSONArray address_components=(JSONArray)((JSONObject)results.get(2)).get("address_components");
                for(int i=0;i<address_components.size();i++)
                {
                    JSONObject items=(JSONObject)address_components.get(i);
                    System.out.println("items=>"+items);
                    //if(((JSONObject)types.get(0)).get("").toString().equals("country"))
                    if(items.get("types").toString().contains("country"))
                    {
                        a.put("country",items.get("long_name").toString());
                        a.put("iso",items.get("short_name").toString());
                        break;
                    }
                }
            }
        }
        catch(Exception ex)
        {
            a=null;
            System.out.println("Error Google Geocoding: " + ex);
        }
        return a;
    }
    
    /**
     * 
     * @param latitude
     * @param longitude
     * @return 
     */
    public static HashMap getElevation(double latitude, double longitude)
    {
        HashMap a=new HashMap();
        try
        {
            URL url = new URL(RepositoryGoogle.elevation_google_url_send_json + "locations=" + String.valueOf(latitude) + "," + String.valueOf(longitude));
            URL file_url = new URL(url.getProtocol() + "://" + url.getHost() + signRequest(url.getPath(), url.getQuery()));
            //BufferedReader lector=new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedReader lector=new BufferedReader(new InputStreamReader(file_url.openStream()));
            String textJson="",tempJs;
            while((tempJs=lector.readLine()) != null)
                textJson+=tempJs;
            if(textJson==null)
                throw new Exception("Don't found item" );
            JSONObject google=((JSONObject)JSONValue.parse(textJson));            
            a.put("status", google.get("status").toString());
            if(a.get("status").toString().equals("OK"))
            {
                JSONArray results=(JSONArray)google.get("results");
//                System.out.println("results=>"+results);                
                JSONObject data =(JSONObject)results.get(0);
//                System.out.println("elevation=>"+data.get("elevation"));
                a.put("elevation",data.get("elevation").toString());
            }
        }
        catch(Exception ex)
        {
            a=null;
            System.out.println("Error Google Elevation: " + ex);
        }
        return a;
    }
    
    /**
     * Method that Generate a key from Hashmap
     * @param values
     * @return 
     */
    public static String generateKey(String[] values)
    {
        String a="";
        for(int i=0;i<values.length;i++)
            if(values[i] != null && !values[i].equals(""))
                a+=RepositoryGoogle.deleteAccent(values[i]).trim().replaceAll(" ", "_") + "_";        
        return RepositoryGoogle.removePatternEnd(a, "_").toLowerCase();
    }
    
    /**
     * Method that delete all accent so leave the string only with ascii characters
     * @param input String to cleaner
     * @return
     */
    public static String deleteAccent(String input)
    {
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        String ascii =    "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i=0; i<original.length(); i++)
            output = output.replace(original.charAt(i), ascii.charAt(i));       
        return output;
    }
    
    /**
     * Method that delete pattern in the end of string
     * @param value value that you need evaluate
     * @param pattern pattern that you need delete
     * @return 
     */
    public static String removePatternEnd(String value,String pattern)
    {
        String a=value;
        while(a.endsWith(pattern))
            a=a.substring(0,a.length()-1);
        return a;
    }
    
    /**
     * Method that get distance between two coordinates
     * @param coord1 Coordinates Northeast 
     * @param coord2 Coordinates Southwest
     * @return 
     */
    public static double getDistance(double[] coord1, double[] coord2) {
        if (coord1.length == 2 && coord2.length == 2) {
            double LatA = (coord1[0] * Math.PI) / 180;
            double LatB = (coord2[0] * Math.PI) / 180;
            double LngA = (coord1[1] * Math.PI) / 180;
            double LngB = (coord2[1] * Math.PI) / 180;
            // Retorna la distancia en kilometros
            return 6371 * Math.acos(Math.cos(LatA) * Math.cos(LatB) * Math.cos(LngB - LngA) + Math.sin(LatA) * Math.sin(LatB));
        } else {
            System.out.println("Error: coord length is not correct");
            return -1;
        }
    }
}
