package com.spring.project.development.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

public class DropdownDTO {

    public static Comparator<DropdownDTO> sort = new Comparator<DropdownDTO>() {
        @Override
        public int compare(DropdownDTO o1, DropdownDTO o2) {
            return o1.getText().compareTo(o2.getText());
        }
    };

    // TODO : can be reduce field by using object for value field
    private String value;
    private String text;
    private String valueText;
    private Short valueShort;
    private Byte valueByte;
    private Integer valueInteger;
    private BigInteger valueBigInteger;
    private Character valueChar;
    private BigDecimal valueBigDecimal;

    public DropdownDTO() {
    }

    public DropdownDTO(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public DropdownDTO(String text, Short valueShort) {
        this.text = text;
        this.valueShort = valueShort;
    }

    public DropdownDTO(String text, Integer valueInteger) {
        this.text = text;
        this.valueInteger = valueInteger;
    }

    public DropdownDTO(Character valueChar, String text) {
        this.valueChar = valueChar;
        this.text = text;
    }

    public DropdownDTO(Byte valueByte, String text) {
        this.valueByte = valueByte;
        this.text = text;
    }

    public DropdownDTO(BigDecimal valueBigDecimal, String text) {
        this.valueBigDecimal = valueBigDecimal;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Short getValueShort() {
        return valueShort;
    }

    public void setValueShort(Short valueShort) {
        this.valueShort = valueShort;
    }

    public Byte getValueByte() {
        return valueByte;
    }

    public void setValueByte(Byte valueByte) {
        this.valueByte = valueByte;
    }

    public Integer getValueInteger() {
        return valueInteger;
    }

    public void setValueInteger(Integer valueInteger) {
        this.valueInteger = valueInteger;
    }

    public Character getValueChar() {
        return valueChar;
    }

    public void setValueChar(Character valueChar) {
        this.valueChar = valueChar;
    }

    public BigDecimal getValueBigDecimal() {
        return valueBigDecimal;
    }

    public void setValueBigDecimal(BigDecimal valueBigDecimal) {
        this.valueBigDecimal = valueBigDecimal;
    }

    public static Comparator<DropdownDTO> getSort() {
        return sort;
    }

    public static void setSort(Comparator<DropdownDTO> sort) {
        DropdownDTO.sort = sort;
    }

    public BigInteger getValueBigInteger() {
        return valueBigInteger;
    }

    public void setValueBigInteger(BigInteger valueBigInteger) {
        this.valueBigInteger = valueBigInteger;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((text == null) ? 0 : text.hashCode())
                + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        DropdownDTO other = (DropdownDTO) obj;

        //text
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;

        //value
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;

        return true;
    }
}