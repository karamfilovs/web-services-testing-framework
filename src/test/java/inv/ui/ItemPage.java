package inv.ui;

import com.microsoft.playwright.Page;

public class ItemPage {
    private Page page = null;

    public ItemPage (Page page){
        this.page = page;
    }

    public String createItem(String name){
        page.click("a.newbtn");
        page.fill("input[name=name]", name);
        page.click("input[name=do_submit]");
        return page.textContent("#okmsg");
    }

    public void goTo(){
        page.navigate("https://st2016.inv.bg/objects/manage");
    }

    public String getHeadline(){
        return page.textContent("div#headline h2");
    }
}
