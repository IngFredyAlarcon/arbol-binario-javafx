package co.edu.uptc.Export;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Exporta la representación gráfica del árbol (capturada desde el Pane de JavaFX
 * donde TreeDrawer dibuja el árbol) a un archivo PDF.
 *
 * Vive en infrastructure porque, igual que la persistencia en archivos,
 * es un detalle técnico de "cómo se guarda" y no debe conocerlo el dominio.
 *
 * Nota: la conversión de WritableImage a BufferedImage se hace manualmente,
 * pixel por pixel, para no depender del módulo javafx-swing (SwingFXUtils).
 * BufferedImage es parte de java.awt.image (JDK base), no de Swing.
 */
public class PdfTreeExporter {

    public void export(Pane treeDrawingPanel, String nombreArbol, File archivoDestino) throws IOException {

        WritableImage snapshot = treeDrawingPanel.snapshot(new SnapshotParameters(), null);
        BufferedImage bufferedImage = convertirAImagenAwt(snapshot);

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);

            float anchoPagina = page.getMediaBox().getWidth();
            float altoPagina = page.getMediaBox().getHeight();

            float escala = Math.min(
                    (anchoPagina - 100) / pdImage.getWidth(),
                    (altoPagina - 150) / pdImage.getHeight()
            );
            escala = Math.min(escala, 1f);

            float anchoFinal = pdImage.getWidth() * escala;
            float altoFinal = pdImage.getHeight() * escala;
            float x = (anchoPagina - anchoFinal) / 2;
            float y = altoPagina - altoFinal - 100;

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                contentStream.newLineAtOffset(50, altoPagina - 60);
                contentStream.showText("Árbol: " + nombreArbol);
                contentStream.endText();

                contentStream.drawImage(pdImage, x, y, anchoFinal, altoFinal);
            }

            document.save(archivoDestino);
        }
    }

    private BufferedImage convertirAImagenAwt(WritableImage imagenFX) {
        int ancho = (int) Math.round(imagenFX.getWidth());
        int alto = (int) Math.round(imagenFX.getHeight());

        BufferedImage bufferedImage = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        PixelReader lector = imagenFX.getPixelReader();

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                bufferedImage.setRGB(x, y, lector.getArgb(x, y));
            }
        }

        return bufferedImage;
    }
}
