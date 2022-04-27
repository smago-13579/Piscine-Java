package edu.school21.reflection.processor;

import com.google.auto.service.AutoService;
import edu.school21.reflection.annotations.HtmlForm;
import edu.school21.reflection.annotations.HtmlInput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes(
        "edu.school21.reflection.annotations.HtmlForm")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);

        for (Element element : annotatedElements) {
            HtmlForm annotation = element.getAnnotation(HtmlForm.class);
            List<? extends Element> enclosedElements = element.getEnclosedElements();
            List<Annotation> fields = enclosedElements.stream().map(e -> e.getAnnotation(HtmlInput.class))
                    .filter(Objects::nonNull).collect(Collectors.toList());
            try {
                createSourceFile(annotation, fields);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    private void createSourceFile(HtmlForm form, List<Annotation> fields) throws IOException {
        FileObject builderFile = processingEnv.getFiler()
                .createResource(StandardLocation.CLASS_OUTPUT, "", form.fileName());

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            out.println("<form action = \"" + form.action() + "\" "
                    + "method = \"" + form.method() + "\">");

            for (Annotation annotation : fields) {
                HtmlInput input = (HtmlInput) annotation;
                out.println("<input type = \"" + input.type() + "\" "
                        + "name = \"" + input.name() + "\" "
                        + "placeholder = \"" + input.placeholder() + "\">");
            }
            out.println("<input type = \"submit\" value = \"Send\">");
            out.println("</form>");
        }
    }
}
