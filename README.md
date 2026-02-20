__PDF/UA__

Demonstration of some important points of PDF/UA, how compliance with these points in the standard can be achieved.


__PdfUaWithImageAndTitle__

[PdfUaWithImageAndTitle](src/main/java/com/pdfUaWithImageAndTitle/PdfUaWithImageAndTitle.java)

This demonstrates how to create a PDF/UA with the iText API.

Title and image.

Image has alternative text. Can be opened and read aloud in Acrobat, with photo read too:

"A cat"

__HtmlFromHtmlFile__

[HtmlFromHtmlFile](src/main/java/com/htmlFromHtmlFile/HtmlFromHtmlFile.java)

This demonstrates how to convert from HTML, with tagging.

Might prove challenging with elements like forms. 

In such circumstances, the iText API may be preferrable.

__MinimalPdfUAFormWithTooltips__

[MinimalPdfUAFormWithTooltips](src/main/java/com/minimalPdfUAFormWithTooltips/MinimalPdfUAFormWithTooltips.java)

How to create a compliant form with tool tips, using the iText API. 

__DecorativeElementTaggedAsArtifact__

[DecorativeElementTaggedAsArtifact](src/main/java/com/decorativeElementTaggedAsArtifact/DecorativeElementTaggedAsArtifact.java)

Demonstrates how to deal with decorative elements which do not have any relevance for a screenreader.

Such elements should be tagged as artifacts.

Example shown: border.

__SpecialCharactersActualText__

[SpecialCharactersActualText](src/main/java/com/specialCharactersActualText/SpecialCharactersActualText.java)

SpecialCharactersActualText shows how to treat special characters that might not be read correctly by a screenreader.

Here, ∜ / fourth root. 

