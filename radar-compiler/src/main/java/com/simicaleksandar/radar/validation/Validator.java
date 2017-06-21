package com.simicaleksandar.radar.validation;


import javax.lang.model.element.Element;

public class Validator {

    private void validate(Element annotatedElement, Kind kind) {
        try {
            switch (kind) {
                case VIEW_HOLDER:
                    ViewHolderValidator.validate(annotatedElement);
            }
        } catch (ValidationException e) {

        }
    }
}
