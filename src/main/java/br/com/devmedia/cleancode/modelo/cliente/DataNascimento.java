/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Márcio M. Noda
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.infraestrutura.DateTimeUtils;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.isNull;

public class DataNascimento implements Serializable, Comparable<DataNascimento> {

    private static final long serialVersionUID = -6725740646133455799L;

    private final LocalDate data;

    private DataNascimento(LocalDate data) {
        checkArgument(!isNull(data), "Data nulo");
        this.data = data;
    }

    public static DataNascimento valueOf(LocalDate data) {
        return new DataNascimento(data);
    }

    public static DataNascimento valueOf(Date data) {
        return valueOf(data.toLocalDate());
    }

    public static DataNascimento of(int ano, int mes, int dia) {
        return valueOf(LocalDate.of(ano, mes, dia));
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DataNascimento) {
            final DataNascimento other = (DataNascimento) obj;
            return Objects.equals(this.data, other.data);
        }
        return false;
    }

    @Override
    public String toString() {
        return data.format(ofPattern("dd/MM/yyyy"));
    }

    public LocalDate toLocalDate() {
        return data;
    }

    public Idade getIdade() {
        Period periodo = Period.between(data, DateTimeUtils.INSTANCE.newLocalDate());
        return Idade.valueOf(periodo.getYears());
    }

    @Override
    public int compareTo(DataNascimento o) {
        return data.compareTo(o.data);
    }
}
