package com.ff.tool;

import java.util.regex.Pattern;

public final class MyRegex {

	public final static boolean chekMail(final String mail){
		String regex = "^\\w(\\.?[\\w-])*@\\w(\\.?[-\\w])*\\.[a-z](([a-z]*)(\\.[a-z]([a-z])*)?|([a-z])*(\\.[a-z]([a-z])*)?)$";
		return Pattern.matches(regex, mail.toLowerCase());
	}

	public final static boolean chekIP(final String ip){
		String regex = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$";
		return Pattern.matches(regex, ip.toLowerCase());
	}

	public final static boolean chekHeure(final String heure){
		String regex = "^[0-2][0-9]:[0-5][0-9]$";
		return Pattern.matches(regex, heure.toLowerCase());
	}

	public static boolean chekCP(String cp) {
		String regex = "^(0[1-9]|[1-9][0-9])[0-9]{3}$";
		return Pattern.matches(regex, cp.toLowerCase());
	}

	public static boolean chekTelephone(String telephone) {
		String regex = "^0[1-9]([0-9]{8})$";
		return Pattern.matches(regex, telephone);
	}

	public static boolean chekMondialTelephone(String telephone) {
		String regex = "[0-9]*";
		return Pattern.matches(regex, telephone);
	}


	public static boolean chekSiret(String siret) {
		String regex = "^[0-9]{14}$";
		return Pattern.matches(regex, siret);
	}

	public static boolean chekApe(String ape) {
		String regex = "^[0-9]{3}[A-Z]$";
		return Pattern.matches(regex, ape.toUpperCase());
	}

	public static boolean chekServeur(String serveur) {
		String regex = "^ipbx\\.voip-centrex\\.net";
		return Pattern.matches(regex, serveur.toUpperCase());
	}

	public static boolean chekSubPoste(String subPoste){
		String regex = "^[0-9]*\\.\\w*";
		return Pattern.matches(regex, subPoste);
	}

	public static boolean chekSerialUC500(String ns){
		String regex = "^([A-Z]{3})([0-9A-Z]{8})$";
		return Pattern.matches(regex, ns.toUpperCase());
	}

	public static boolean chekFileHtml(String fileName) {
		String regex = "^.+\\.html$";
		return Pattern.matches(regex, fileName.toLowerCase());
	}

	public static boolean chekSiren(String siren) {
		String regex = "^[0-9]{9}$";
		return Pattern.matches(regex, siren);
	}

	public static boolean chekTva(String tva) {
		String regex = "^FR[0-9A-Z]{2}[0-9]{9}$";
		return Pattern.matches(regex, tva.toUpperCase());
	}

	public static boolean chekFilePdf(String filename){
		String regex = "^.+\\.pdf$";
		return Pattern.matches(regex, filename.toLowerCase());
	}

	public static boolean checkSNMPName(String nom){
		String regex = "^load_1min$";
		if (Pattern.matches(regex, nom.toLowerCase()))
			return true;
		regex = "^load_1min$";
		if (Pattern.matches(regex, nom.toLowerCase()))
			return true;
		regex = "^load_5min$";
		if (Pattern.matches(regex, nom.toLowerCase()))
			return true;
		regex = "^load_15min$";
		if (Pattern.matches(regex, nom.toLowerCase()))
			return true;
		regex = "^mem_buffers$";
		if (Pattern.matches(regex, nom.toLowerCase()))
			return true;
		regex = "^mem_free$";
		if (Pattern.matches(regex, nom.toLowerCase()))
			return true;
		regex = "^mem_cache$";
		if (Pattern.matches(regex, nom.toLowerCase()))
			return true;
		regex = "^cpu_system$";
		if (Pattern.matches(regex, nom.toLowerCase()))
			return true;
		regex = "^cpu_nice$";
		if (Pattern.matches(regex, nom.toLowerCase()))
			return true;
		regex = "^cpu_user$";
		if (Pattern.matches(regex, nom.toLowerCase()))
			return true;
		regex = "^eth([0-9]{1,3})_traffic_in$";
		if (Pattern.matches(regex, nom.toLowerCase()))
			return true;
		regex = "^eth([0-9]{1,3})_traffic_out$";
		if (Pattern.matches(regex, nom.toLowerCase()))
			return true;
		return false;
	}

}
