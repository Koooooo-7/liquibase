package liquibase.command.core;

import liquibase.command.*;
import liquibase.integration.commandline.Main;

public class MigrateSQLCommandStep extends AbstractCliWrapperCommandStep {
    public static final CommandArgumentDefinition<String> URL_ARG;
    public static final CommandArgumentDefinition<String> USERNAME_ARG;
    public static final CommandArgumentDefinition<String> PASSWORD_ARG;
    public static final CommandArgumentDefinition<String> CHANGELOG_FILE_ARG;
    public static final CommandArgumentDefinition<String> LABELS_ARG;
    public static final CommandArgumentDefinition<String> CONTEXTS_ARG;
    public static final CommandArgumentDefinition<String> OUTPUT_FILE_ARG;

    static {
        CommandStepBuilder builder = new CommandStepBuilder(MigrateSQLCommandStep.class);
        URL_ARG = builder.argument("url", String.class).required()
            .description("The JDBC database connection URL").build();
        USERNAME_ARG = builder.argument("username", String.class)
            .description("Username to use to connect to the database").build();
        PASSWORD_ARG = builder.argument("password", String.class)
            .description("Password to use to connect to the database").build();
        CHANGELOG_FILE_ARG = builder.argument("changeLogFile", String.class)
            .description("File to write changelog to").build();
        LABELS_ARG = builder.argument("labels", String.class)
            .description("Label expression to use for filtering which changes to migrate").build();
        CONTEXTS_ARG = builder.argument("contexts", String.class)
            .description("Context string to use for filtering which changes to migrate").build();
        OUTPUT_FILE_ARG = builder.argument("outputFile", String.class)
            .description("File for writing the SQL").build();
    }

    @Override
    public String[] getName() {
        return new String[] {"migrateSQL"};
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();

        String[] args = createArgs(commandScope);
        int statusCode = Main.run(args);
        addStatusMessage(resultsBuilder, statusCode);
        resultsBuilder.addResult("statusCode", statusCode);
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Write the SQL to deploy changes from the changelog file that have not yet been deployed");
        commandDefinition.setLongDescription("Write the SQL deploy changes from the changelog file that have not yet been deployed");
    }
}
