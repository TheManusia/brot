package xyz.themanusia.brot.listner;

import xyz.themanusia.brot.constant.DBText;

public class ExampleCommand extends Command {
    public ExampleCommand() {
        name = "testcmd";
        description = "testing om";
        aliasses = new String[]{"tst", "test"};
        argument = new String[]{"name"};
    }

    @Override
    protected void execute(CommandEvent event) {
        event.getMessage().reply("Halo bang " + event.getArgs().get(event.getArgs().size() - 1)).queue(s ->
                s.editMessage("3 Detik bang").queueAfter(DBText.DELAY, DBText.TIME));
    }
}
