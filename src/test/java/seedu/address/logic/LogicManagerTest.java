package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.FRIEND_ID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFriends.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.friends.AddFriendCommand;
import seedu.address.logic.commands.friends.DeleteFriendCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyFriendsList;
import seedu.address.model.UserPrefs;
import seedu.address.model.friend.Friend;
import seedu.address.storage.JsonFriendsListStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.FriendBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private final Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonFriendsListStorage addressBookStorage =
                new JsonFriendsListStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = DeleteFriendCommand.COMMAND_WORD + " daksjhdkjashdjkashkdjashjkaskjdjkasdhkas";
        // TODO Update after delete command is updated
        // assertCommandException(deleteCommand, MESSAGE_NONEXISTENT_FRIEND_ID);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        // no flag will default to listing all friends
        // TODO Update after list command is updated
        // String listCommand = ListFriendCommand.COMMAND_WORD;
        // assertCommandSuccess(listCommand,
        //         String.format(ListFriendCommand.MESSAGE_SUCCESS_PREPEND, ListFriendCommand.FRIEND_LIST), model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonFriendsListStorage addressBookStorage =
                new JsonFriendsListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addFriendCommand = AddFriendCommand.COMMAND_WORD + FRIEND_ID_DESC_AMY + NAME_DESC_AMY;
        Friend expectedFriend = new FriendBuilder(AMY).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addFriend(expectedFriend);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        // TODO Update after add command is updated
        // assertCommandFailure(addFriendCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredFriendsList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getFriendsList(), model.getGamesList(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonFriendsListIoExceptionThrowingStub extends JsonFriendsListStorage {
        private JsonFriendsListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyFriendsList addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
