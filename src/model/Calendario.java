/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Thainan
 */
public class Calendario {
    public static String[] listaMes = {"Janeiro", "Fevereiro", "Março", "Abril", 
    "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro",
    "Dezembro"};
    public static String[] listaAno = {"2016", "2015", "2014", "2013", "2012", 
    "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", 
    "2002", "2001", "2000", "1999", "1998", "1997", "1996", "1995", "1994", 
    "1993", "1992", "1991", "1990"};

    public static int mesToInt(String mes) {
        int i;
        for(i = 0; i < listaMes.length; i++) {
            if(listaMes[i].equals(mes)) {
                return i + 1;
            }
        }
        
        return 0;
    }
}