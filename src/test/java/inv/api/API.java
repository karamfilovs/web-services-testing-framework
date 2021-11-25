package inv.api;

public class API {
    private String token = null;
    private ItemAPI itemAPI = null;
    private ClientAPI clientAPI = null;
    //private InvoicesAPI invoicesAPI = null;

    public API(String token){
        this.token = token;
    }

    public ClientAPI clientAPI(){
        if(clientAPI == null){
            clientAPI = new ClientAPI(token);
        }
        return clientAPI;
    }

    public ItemAPI itemAPI(){
        if(itemAPI == null){
            itemAPI = new ItemAPI(token);
        }
        return itemAPI;
    }
}
