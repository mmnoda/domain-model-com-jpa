package br.com.devmedia.cleancode.modelo.cliente;

import br.com.devmedia.cleancode.infraestrutura.DateTimeUtils;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 *
 */
public class DataNascimento implements Serializable, Comparable<DataNascimento> {

    private static final long serialVersionUID = -6725740646133455799L;

    private final LocalDate data;

    private DataNascimento(LocalDate data) {
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
