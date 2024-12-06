package com.smartheusys.eurochem.input;

import lombok.Data;

/**
 * This class models a line of the input data. As we disregard most of the content, we store just the
 * information that we need. This is:
 * <ul>
 *     <li>F_PRODUCT</li>
 *     <li>Data Code Text</li>
 *     <li>F_DATA</li>
 *     <li>F_B_TEXT_LINE</li>
 *     <li>F_REP_SEQUENCE</li>
 * </ul>
 *
 * The F_PRODUCT is an identifier of a fertiliser product. The Data Code Text is a description of the data
 * contained. It is split into value, unit and the description. The F_DATA and F_B_TEXT_LINE are the actual
 * values. The F_REP_SEQUENCE is a sequence number that is used to group the three lines containing the relevant
 * data together.
 */
@Data
public class InputLine {
    private String fProduct;
    private String dataCodeText;
    private String fData;
    private String fBTextLine;
    private String fRepSequence;
}
