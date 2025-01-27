package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.FLAG_GAME_OLD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditFriendDescriptor;
import seedu.address.model.friend.FriendName;
import seedu.address.testutil.EditFriendDescriptorBuilder;

public class EditCommandParserTest {

    private static final String GAME_EMPTY = " " + FLAG_GAME_OLD;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, FriendName.MESSAGE_CONSTRAINTS); // invalid name
        //        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        //        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        //        assertParseFailure(parser, "1"
        //              + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        //        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag
        //
        //        // invalid phone followed by valid email
        //        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);
        //
        //        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        //        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        //        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
        //
        //        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        //        // parsing it together with a valid tag results in error
        //        assertParseFailure(parser, "1" + TAG_DESC_FRIEND
        //        + TAG_DESC_HUSBAND + GAME_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        //        assertParseFailure(parser, "1" + TAG_DESC_FRIEND
        //        + GAME_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        //        assertParseFailure(parser, "1" + GAME_EMPTY
        //        + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        //
        //        // multiple invalid values, but only the first invalid value is captured
        //        assertParseFailure(parser, "1" + INVALID_NAME_DESC
        //        + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
        //                FriendName.MESSAGE_CONSTRAINTS);
    }
    //
    //    @Test
    //    public void parse_allFieldsSpecified_success() {
    //        Index targetIndex = INDEX_SECOND_PERSON;
    //        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
    //                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;
    //
    //        EditCommand.EditFriendDescriptor descriptor = new EditFriendDescriptorBuilder()
    //                .withFriendName(VALID_NAME_AMY)
    //                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
    //                .withTags(VALID_GAME_CSGO, VALID_GAME_APEX_LEGENDS).build();
    //        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //    }
    //
    //    @Test
    //    public void parse_someFieldsSpecified_success() {
    //        Index targetIndex = INDEX_FIRST_PERSON;
    //        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;
    //
    //        EditCommand.EditFriendDescriptor descriptor = new EditFriendDescriptorBuilder().withPhone(VALID_PHONE_BOB)
    //                .withEmail(VALID_EMAIL_AMY).build();
    //        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //    }
    //
    //    @Test
    //    public void parse_oneFieldSpecified_success() {
    //        // name
    //        Index targetIndex = INDEX_THIRD_PERSON;
    //        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
    //        EditCommand.EditFriendDescriptor descriptor = new EditFriendDescriptorBuilder()
    //              .withFriendName(VALID_NAME_AMY).build();
    //        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //
    //        // phone
    //        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
    //        descriptor = new EditFriendDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
    //        expectedCommand = new EditCommand(targetIndex, descriptor);
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //
    //        // email
    //        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
    //        descriptor = new EditFriendDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
    //        expectedCommand = new EditCommand(targetIndex, descriptor);
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //
    //        // address
    //        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
    //        descriptor = new EditFriendDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
    //        expectedCommand = new EditCommand(targetIndex, descriptor);
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //
    //        // tags
    //        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
    //        descriptor = new EditFriendDescriptorBuilder().withGames(VALID_GAME_APEX_LEGENDS).build();
    //        expectedCommand = new EditCommand(targetIndex, descriptor);
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //    }
    //
    //    @Test
    //    public void parse_multipleRepeatedFields_acceptsLast() {
    //        Index targetIndex = INDEX_FIRST_PERSON;
    //        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
    //                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
    //                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;
    //
    //        EditCommand.EditFriendDescriptor descriptor = new EditFriendDescriptorBuilder().withPhone(VALID_PHONE_BOB)
    //                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
    //                .withTags(VALID_GAME_APEX_LEGENDS, VALID_GAME_CSGO)
    //                .build();
    //        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //    }
    //
    //    @Test
    //    public void parse_invalidValueFollowedByValidValue_success() {
    //        // no other valid values specified
    //        Index targetIndex = INDEX_FIRST_PERSON;
    //        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
    //        EditCommand.EditFriendDescriptor descriptor = new EditFriendDescriptorBuilder()
    //              .withPhone(VALID_PHONE_BOB).build();
    //        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //
    //        // other valid values specified
    //        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
    //                + PHONE_DESC_BOB;
    //        descriptor = new EditFriendDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
    //                .withAddress(VALID_ADDRESS_BOB).build();
    //        expectedCommand = new EditCommand(targetIndex, descriptor);
    //        assertParseSuccess(parser, userInput, expectedCommand);
    //    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + GAME_EMPTY;

        EditFriendDescriptor descriptor = new EditFriendDescriptorBuilder().withGames().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
