package codeemoji.inlay;

import codeemoji.core.CEMethodCollector;
import codeemoji.core.CEProvider;
import com.intellij.codeInsight.hints.InlayHintsCollector;
import com.intellij.codeInsight.hints.InlayHintsSink;
import com.intellij.codeInsight.hints.NoSettings;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static codeemoji.core.CESymbol.CONFUSED;

public class NotAnsweredQuestion extends CEProvider<NoSettings> {

    @Override
    public @Nullable String getPreviewText() {
        return """
                public class Customer {
                    public void isHuman() {
                        doSomething();
                    }
                }""";
    }

    @Override
    public InlayHintsCollector getCollector(@NotNull Editor editor, @NotNull String keyId) {
        return new CEMethodCollector(editor, keyId) {
            @Override
            public void execute(@Nullable PsiMethod method, InlayHintsSink sink) {
                if ((method != null && method.getName().startsWith("is") &&
                        Objects.equals(method.getReturnType(), PsiTypes.voidType()))) {
                    addInlayHint(Objects.requireNonNull(method.getNameIdentifier()), sink, CONFUSED);
                }
            }
        };

    }
}








