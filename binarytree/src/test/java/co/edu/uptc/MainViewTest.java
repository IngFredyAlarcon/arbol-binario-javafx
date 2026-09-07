package co.edu.uptc;

import org.junit.jupiter.api.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MainViewTest extends ApplicationTest {

        @Override
        public void start(Stage stage) throws Exception {

                FXMLLoader loader = new FXMLLoader(
                                getClass().getResource(
                                                "/co/edu/uptc/fxml/MainView.fxml"));

                Parent root = loader.load();

                stage.setScene(new Scene(root));
                stage.show();
        }

        // ========================================
        // VISUAL TESTS
        // ========================================

        @Test
        void shouldShowTreeCombo() {

                verifyThat("#comboTrees", isVisible());
        }

        @Test
        void shouldShowCreateTreeButton() {

                verifyThat("#btnNewTree", isVisible());

                verifyThat("#btnNewTree",
                                hasText("Crear árbol"));
        }

        @Test
        void shouldShowLoadTreeButton() {

                verifyThat("#btnLoadTree", isVisible());

                verifyThat("#btnLoadTree",
                                hasText("Cargar árbol"));
        }

        @Test
        void shouldShowSaveTreeButton() {

                verifyThat("#btnSaveTree", isVisible());

                verifyThat("#btnSaveTree",
                                hasText("Guardar árbol"));
        }

        @Test
        void shouldShowDeleteTreeButton() {

                verifyThat("#btnDeleteTree", isVisible());

                verifyThat("#btnDeleteTree",
                                hasText("Eliminar árbol"));
        }

        @Test
        void shouldShowValueField() {

                verifyThat("#valueField", isVisible());
        }

        @Test
        void shouldShowOperationButtons() {

                verifyThat("#btnInsert", isVisible());
                verifyThat("#btnSearch", isVisible());
                verifyThat("#btnDeleteValue", isVisible());
        }

        @Test
        void shouldShowTraversalButtons() {

                verifyThat("#btnPreorder", isVisible());
                verifyThat("#btnInorder", isVisible());
                verifyThat("#btnPostorder", isVisible());
        }

        @Test
        void shouldShowTreeDrawingPanel() {

                verifyThat("#treeDrawingPanel", isVisible());
        }

        @Test
        void messagesAreaShouldBeReadOnly() {

                TextArea messagesArea = lookup("#messagesArea")
                                .queryAs(TextArea.class);

                org.junit.jupiter.api.Assertions.assertFalse(
                                messagesArea.isEditable());
        }

        // ========================================
        // INTERACTION TESTS
        // ------------------------------------------------------------
        // Comentados temporalmente: dependen de que MainViewController
        // implemente la lógica real de cada acción (por ahora los
        // métodos @FXML son stubs vacíos y messagesArea no recibe
        // ningún texto). Descomentar cuando esos métodos queden
        // implementados.
        // ========================================

        /*
         * @Test
         * void shouldShowMessagesArea() {
         * 
         * verifyThat("#messagesArea", isVisible());
         * 
         * TextArea messagesArea = lookup("#messagesArea")
         * .queryAs(TextArea.class);
         * 
         * org.junit.jupiter.api.Assertions.assertEquals(
         * "Aplicación iniciada. Crea un nuevo árbol para comenzar.\n",
         * messagesArea.getText()
         * );
         * }
         * 
         * @Test
         * void loadButtonShouldShowMessage() {
         * 
         * clickOn("#btnLoadTree");
         * 
         * TextArea messagesArea = lookup("#messagesArea")
         * .queryAs(TextArea.class);
         * 
         * org.junit.jupiter.api.Assertions.assertEquals(
         * "Aplicación iniciada. Crea un nuevo árbol para comenzar.\n" +
         * "Función de carga en desarrollo...\n",
         * messagesArea.getText()
         * );
         * }
         * 
         * @Test
         * void saveButtonShouldShowMessage() {
         * 
         * clickOn("#btnSaveTree");
         * 
         * TextArea messagesArea = lookup("#messagesArea")
         * .queryAs(TextArea.class);
         * 
         * org.junit.jupiter.api.Assertions.assertEquals(
         * "Aplicación iniciada. Crea un nuevo árbol para comenzar.\n" +
         * "Función de guardado en desarrollo...\n",
         * messagesArea.getText()
         * );
         * }
         * 
         * @Test
         * void insertButtonShouldShowMessage() {
         * 
         * clickOn("#btnInsert");
         * 
         * TextArea messagesArea = lookup("#messagesArea")
         * .queryAs(TextArea.class);
         * 
         * org.junit.jupiter.api.Assertions.assertEquals(
         * "Aplicación iniciada. Crea un nuevo árbol para comenzar.\n" +
         * "Función de inserción en desarrollo...\n",
         * messagesArea.getText()
         * );
         * }
         * 
         * @Test
         * void searchButtonShouldShowMessage() {
         * 
         * clickOn("#btnSearch");
         * 
         * TextArea messagesArea = lookup("#messagesArea")
         * .queryAs(TextArea.class);
         * 
         * org.junit.jupiter.api.Assertions.assertEquals(
         * "Aplicación iniciada. Crea un nuevo árbol para comenzar.\n" +
         * "Función de búsqueda en desarrollo...\n",
         * messagesArea.getText()
         * );
         * }
         * 
         * @Test
         * void deleteValueButtonShouldShowMessage() {
         * 
         * clickOn("#btnDeleteValue");
         * 
         * TextArea messagesArea = lookup("#messagesArea")
         * .queryAs(TextArea.class);
         * 
         * org.junit.jupiter.api.Assertions.assertEquals(
         * "Aplicación iniciada. Crea un nuevo árbol para comenzar.\n" +
         * "Función de eliminación de valor en desarrollo...\n",
         * messagesArea.getText()
         * );
         * }
         * 
         * @Test
         * void preorderButtonShouldShowMessage() {
         * 
         * clickOn("#btnPreorder");
         * 
         * TextArea messagesArea = lookup("#messagesArea")
         * .queryAs(TextArea.class);
         * 
         * org.junit.jupiter.api.Assertions.assertEquals(
         * "Aplicación iniciada. Crea un nuevo árbol para comenzar.\n" +
         * "Recorrido preorden en desarrollo...\n",
         * messagesArea.getText()
         * );
         * }
         * 
         * @Test
         * void inorderButtonShouldShowMessage() {
         * 
         * clickOn("#btnInorder");
         * 
         * TextArea messagesArea = lookup("#messagesArea")
         * .queryAs(TextArea.class);
         * 
         * org.junit.jupiter.api.Assertions.assertEquals(
         * "Aplicación iniciada. Crea un nuevo árbol para comenzar.\n" +
         * "Recorrido inorden en desarrollo...\n",
         * messagesArea.getText()
         * );
         * }
         * 
         * @Test
         * void postorderButtonShouldShowMessage() {
         * 
         * clickOn("#btnPostorder");
         * 
         * TextArea messagesArea = lookup("#messagesArea")
         * .queryAs(TextArea.class);
         * 
         * org.junit.jupiter.api.Assertions.assertEquals(
         * "Aplicación iniciada. Crea un nuevo árbol para comenzar.\n" +
         * "Recorrido postorden en desarrollo...\n",
         * messagesArea.getText()
         * );
         * }
         * 
         * @Test
         * void deletingTreeWithoutSelectionShouldShowError() {
         * 
         * clickOn("#btnDeleteTree");
         * 
         * TextArea messagesArea = lookup("#messagesArea")
         * .queryAs(TextArea.class);
         * 
         * org.junit.jupiter.api.Assertions.assertEquals(
         * "Aplicación iniciada. Crea un nuevo árbol para comenzar.\n" +
         * " Selecciona un árbol para eliminar.\n",
         * messagesArea.getText()
         * );
         * }
         */
}