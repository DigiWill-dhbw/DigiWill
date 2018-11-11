package digiwill;


import java.util.ArrayList;
import java.util.List;

public class EmailAction extends BaseAction {
    private List<String> recipients = new ArrayList<>();
    private String subject;
    private boolean isHTMLContent;
    private String content;

    @Override
    public void execute(SystemHandle systemHandle) {//TODO implement

    }
}
