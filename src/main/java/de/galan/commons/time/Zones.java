package de.galan.commons.time;

import java.time.ZoneId;
import java.util.concurrent.ConcurrentHashMap;


/**
 * ZoneIds without using String. Because the availability of the ZoneIds depends on the JVM and version, using constants
 * would result in exceptions when accessing one of them, and another not related one does not exists. Therefore methods
 * which cache the ZoneId are used.
 */
public class Zones {

	private final static ConcurrentHashMap<String, ZoneId> ZONE_IDS = new ConcurrentHashMap<>();


	private static ZoneId getZoneId(String zoneId) {
		return ZONE_IDS.computeIfAbsent(zoneId, ZoneId::of);
	}


	public static ZoneId africaAbidjan() {
		return getZoneId("Africa/Abidjan");
	}


	public static ZoneId africaAccra() {
		return getZoneId("Africa/Accra");
	}


	public static ZoneId africaAddisAbaba() {
		return getZoneId("Africa/Addis_Ababa");
	}


	public static ZoneId africaAlgiers() {
		return getZoneId("Africa/Algiers");
	}


	public static ZoneId africaAsmara() {
		return getZoneId("Africa/Asmara");
	}


	public static ZoneId africaAsmera() {
		return getZoneId("Africa/Asmera");
	}


	public static ZoneId africaBamako() {
		return getZoneId("Africa/Bamako");
	}


	public static ZoneId africaBangui() {
		return getZoneId("Africa/Bangui");
	}


	public static ZoneId africaBanjul() {
		return getZoneId("Africa/Banjul");
	}


	public static ZoneId africaBissau() {
		return getZoneId("Africa/Bissau");
	}


	public static ZoneId africaBlantyre() {
		return getZoneId("Africa/Blantyre");
	}


	public static ZoneId africaBrazzaville() {
		return getZoneId("Africa/Brazzaville");
	}


	public static ZoneId africaBujumbura() {
		return getZoneId("Africa/Bujumbura");
	}


	public static ZoneId africaCairo() {
		return getZoneId("Africa/Cairo");
	}


	public static ZoneId africaCasablanca() {
		return getZoneId("Africa/Casablanca");
	}


	public static ZoneId africaCeuta() {
		return getZoneId("Africa/Ceuta");
	}


	public static ZoneId africaConakry() {
		return getZoneId("Africa/Conakry");
	}


	public static ZoneId africaDakar() {
		return getZoneId("Africa/Dakar");
	}


	public static ZoneId africaDarEsSalaam() {
		return getZoneId("Africa/Dar_es_Salaam");
	}


	public static ZoneId africaDjibouti() {
		return getZoneId("Africa/Djibouti");
	}


	public static ZoneId africaDouala() {
		return getZoneId("Africa/Douala");
	}


	public static ZoneId africaElAaiun() {
		return getZoneId("Africa/El_Aaiun");
	}


	public static ZoneId africaFreetown() {
		return getZoneId("Africa/Freetown");
	}


	public static ZoneId africaGaborone() {
		return getZoneId("Africa/Gaborone");
	}


	public static ZoneId africaHarare() {
		return getZoneId("Africa/Harare");
	}


	public static ZoneId africaJohannesburg() {
		return getZoneId("Africa/Johannesburg");
	}


	public static ZoneId africaJuba() {
		return getZoneId("Africa/Juba");
	}


	public static ZoneId africaKampala() {
		return getZoneId("Africa/Kampala");
	}


	public static ZoneId africaKhartoum() {
		return getZoneId("Africa/Khartoum");
	}


	public static ZoneId africaKigali() {
		return getZoneId("Africa/Kigali");
	}


	public static ZoneId africaKinshasa() {
		return getZoneId("Africa/Kinshasa");
	}


	public static ZoneId africaLagos() {
		return getZoneId("Africa/Lagos");
	}


	public static ZoneId africaLibreville() {
		return getZoneId("Africa/Libreville");
	}


	public static ZoneId africaLome() {
		return getZoneId("Africa/Lome");
	}


	public static ZoneId africaLuanda() {
		return getZoneId("Africa/Luanda");
	}


	public static ZoneId africaLubumbashi() {
		return getZoneId("Africa/Lubumbashi");
	}


	public static ZoneId africaLusaka() {
		return getZoneId("Africa/Lusaka");
	}


	public static ZoneId africaMalabo() {
		return getZoneId("Africa/Malabo");
	}


	public static ZoneId africaMaputo() {
		return getZoneId("Africa/Maputo");
	}


	public static ZoneId africaMaseru() {
		return getZoneId("Africa/Maseru");
	}


	public static ZoneId africaMbabane() {
		return getZoneId("Africa/Mbabane");
	}


	public static ZoneId africaMogadishu() {
		return getZoneId("Africa/Mogadishu");
	}


	public static ZoneId africaMonrovia() {
		return getZoneId("Africa/Monrovia");
	}


	public static ZoneId africaNairobi() {
		return getZoneId("Africa/Nairobi");
	}


	public static ZoneId africaNdjamena() {
		return getZoneId("Africa/Ndjamena");
	}


	public static ZoneId africaNiamey() {
		return getZoneId("Africa/Niamey");
	}


	public static ZoneId africaNouakchott() {
		return getZoneId("Africa/Nouakchott");
	}


	public static ZoneId africaOuagadougou() {
		return getZoneId("Africa/Ouagadougou");
	}


	public static ZoneId africaPortoNovo() {
		return getZoneId("Africa/Porto-Novo");
	}


	public static ZoneId africaSaoTome() {
		return getZoneId("Africa/Sao_Tome");
	}


	public static ZoneId africaTimbuktu() {
		return getZoneId("Africa/Timbuktu");
	}


	public static ZoneId africaTripoli() {
		return getZoneId("Africa/Tripoli");
	}


	public static ZoneId africaTunis() {
		return getZoneId("Africa/Tunis");
	}


	public static ZoneId africaWindhoek() {
		return getZoneId("Africa/Windhoek");
	}


	public static ZoneId americaAdak() {
		return getZoneId("America/Adak");
	}


	public static ZoneId americaAnchorage() {
		return getZoneId("America/Anchorage");
	}


	public static ZoneId americaAnguilla() {
		return getZoneId("America/Anguilla");
	}


	public static ZoneId americaAntigua() {
		return getZoneId("America/Antigua");
	}


	public static ZoneId americaAraguaina() {
		return getZoneId("America/Araguaina");
	}


	public static ZoneId americaArgentinaBuenosAires() {
		return getZoneId("America/Argentina/Buenos_Aires");
	}


	public static ZoneId americaArgentinaCatamarca() {
		return getZoneId("America/Argentina/Catamarca");
	}


	public static ZoneId americaArgentinaComodRivadavia() {
		return getZoneId("America/Argentina/ComodRivadavia");
	}


	public static ZoneId americaArgentinaCordoba() {
		return getZoneId("America/Argentina/Cordoba");
	}


	public static ZoneId americaArgentinaJujuy() {
		return getZoneId("America/Argentina/Jujuy");
	}


	public static ZoneId americaArgentinaLaRioja() {
		return getZoneId("America/Argentina/La_Rioja");
	}


	public static ZoneId americaArgentinaMendoza() {
		return getZoneId("America/Argentina/Mendoza");
	}


	public static ZoneId americaArgentinaRioGallegos() {
		return getZoneId("America/Argentina/Rio_Gallegos");
	}


	public static ZoneId americaArgentinaSalta() {
		return getZoneId("America/Argentina/Salta");
	}


	public static ZoneId americaArgentinaSanJuan() {
		return getZoneId("America/Argentina/San_Juan");
	}


	public static ZoneId americaArgentinaSanLuis() {
		return getZoneId("America/Argentina/San_Luis");
	}


	public static ZoneId americaArgentinaTucuman() {
		return getZoneId("America/Argentina/Tucuman");
	}


	public static ZoneId americaArgentinaUshuaia() {
		return getZoneId("America/Argentina/Ushuaia");
	}


	public static ZoneId americaAruba() {
		return getZoneId("America/Aruba");
	}


	public static ZoneId americaAsuncion() {
		return getZoneId("America/Asuncion");
	}


	public static ZoneId americaAtikokan() {
		return getZoneId("America/Atikokan");
	}


	public static ZoneId americaAtka() {
		return getZoneId("America/Atka");
	}


	public static ZoneId americaBahia() {
		return getZoneId("America/Bahia");
	}


	public static ZoneId americaBahiaBanderas() {
		return getZoneId("America/Bahia_Banderas");
	}


	public static ZoneId americaBarbados() {
		return getZoneId("America/Barbados");
	}


	public static ZoneId americaBelem() {
		return getZoneId("America/Belem");
	}


	public static ZoneId americaBelize() {
		return getZoneId("America/Belize");
	}


	public static ZoneId americaBlancSablon() {
		return getZoneId("America/Blanc-Sablon");
	}


	public static ZoneId americaBoaVista() {
		return getZoneId("America/Boa_Vista");
	}


	public static ZoneId americaBogota() {
		return getZoneId("America/Bogota");
	}


	public static ZoneId americaBoise() {
		return getZoneId("America/Boise");
	}


	public static ZoneId americaBuenosAires() {
		return getZoneId("America/Buenos_Aires");
	}


	public static ZoneId americaCambridgeBay() {
		return getZoneId("America/Cambridge_Bay");
	}


	public static ZoneId americaCampoGrande() {
		return getZoneId("America/Campo_Grande");
	}


	public static ZoneId americaCancun() {
		return getZoneId("America/Cancun");
	}


	public static ZoneId americaCaracas() {
		return getZoneId("America/Caracas");
	}


	public static ZoneId americaCatamarca() {
		return getZoneId("America/Catamarca");
	}


	public static ZoneId americaCayenne() {
		return getZoneId("America/Cayenne");
	}


	public static ZoneId americaCayman() {
		return getZoneId("America/Cayman");
	}


	public static ZoneId americaChicago() {
		return getZoneId("America/Chicago");
	}


	public static ZoneId americaChihuahua() {
		return getZoneId("America/Chihuahua");
	}


	public static ZoneId americaCoralHarbour() {
		return getZoneId("America/Coral_Harbour");
	}


	public static ZoneId americaCordoba() {
		return getZoneId("America/Cordoba");
	}


	public static ZoneId americaCostaRica() {
		return getZoneId("America/Costa_Rica");
	}


	public static ZoneId americaCreston() {
		return getZoneId("America/Creston");
	}


	public static ZoneId americaCuiaba() {
		return getZoneId("America/Cuiaba");
	}


	public static ZoneId americaCuracao() {
		return getZoneId("America/Curacao");
	}


	public static ZoneId americaDanmarkshavn() {
		return getZoneId("America/Danmarkshavn");
	}


	public static ZoneId americaDawson() {
		return getZoneId("America/Dawson");
	}


	public static ZoneId americaDawsonCreek() {
		return getZoneId("America/Dawson_Creek");
	}


	public static ZoneId americaDenver() {
		return getZoneId("America/Denver");
	}


	public static ZoneId americaDetroit() {
		return getZoneId("America/Detroit");
	}


	public static ZoneId americaDominica() {
		return getZoneId("America/Dominica");
	}


	public static ZoneId americaEdmonton() {
		return getZoneId("America/Edmonton");
	}


	public static ZoneId americaEirunepe() {
		return getZoneId("America/Eirunepe");
	}


	public static ZoneId americaElSalvador() {
		return getZoneId("America/El_Salvador");
	}


	public static ZoneId americaEnsenada() {
		return getZoneId("America/Ensenada");
	}


	public static ZoneId americaFortWayne() {
		return getZoneId("America/Fort_Wayne");
	}


	public static ZoneId americaFortaleza() {
		return getZoneId("America/Fortaleza");
	}


	public static ZoneId americaGlaceBay() {
		return getZoneId("America/Glace_Bay");
	}


	public static ZoneId americaGodthab() {
		return getZoneId("America/Godthab");
	}


	public static ZoneId americaGooseBay() {
		return getZoneId("America/Goose_Bay");
	}


	public static ZoneId americaGrandTurk() {
		return getZoneId("America/Grand_Turk");
	}


	public static ZoneId americaGrenada() {
		return getZoneId("America/Grenada");
	}


	public static ZoneId americaGuadeloupe() {
		return getZoneId("America/Guadeloupe");
	}


	public static ZoneId americaGuatemala() {
		return getZoneId("America/Guatemala");
	}


	public static ZoneId americaGuayaquil() {
		return getZoneId("America/Guayaquil");
	}


	public static ZoneId americaGuyana() {
		return getZoneId("America/Guyana");
	}


	public static ZoneId americaHalifax() {
		return getZoneId("America/Halifax");
	}


	public static ZoneId americaHavana() {
		return getZoneId("America/Havana");
	}


	public static ZoneId americaHermosillo() {
		return getZoneId("America/Hermosillo");
	}


	public static ZoneId americaIndianaIndianapolis() {
		return getZoneId("America/Indiana/Indianapolis");
	}


	public static ZoneId americaIndianaKnox() {
		return getZoneId("America/Indiana/Knox");
	}


	public static ZoneId americaIndianaMarengo() {
		return getZoneId("America/Indiana/Marengo");
	}


	public static ZoneId americaIndianaPetersburg() {
		return getZoneId("America/Indiana/Petersburg");
	}


	public static ZoneId americaIndianaTellCity() {
		return getZoneId("America/Indiana/Tell_City");
	}


	public static ZoneId americaIndianaVevay() {
		return getZoneId("America/Indiana/Vevay");
	}


	public static ZoneId americaIndianaVincennes() {
		return getZoneId("America/Indiana/Vincennes");
	}


	public static ZoneId americaIndianaWinamac() {
		return getZoneId("America/Indiana/Winamac");
	}


	public static ZoneId americaIndianapolis() {
		return getZoneId("America/Indianapolis");
	}


	public static ZoneId americaInuvik() {
		return getZoneId("America/Inuvik");
	}


	public static ZoneId americaIqaluit() {
		return getZoneId("America/Iqaluit");
	}


	public static ZoneId americaJamaica() {
		return getZoneId("America/Jamaica");
	}


	public static ZoneId americaJujuy() {
		return getZoneId("America/Jujuy");
	}


	public static ZoneId americaJuneau() {
		return getZoneId("America/Juneau");
	}


	public static ZoneId americaKentuckyLouisville() {
		return getZoneId("America/Kentucky/Louisville");
	}


	public static ZoneId americaKentuckyMonticello() {
		return getZoneId("America/Kentucky/Monticello");
	}


	public static ZoneId americaKnoxIN() {
		return getZoneId("America/Knox_IN");
	}


	public static ZoneId americaKralendijk() {
		return getZoneId("America/Kralendijk");
	}


	public static ZoneId americaLaPaz() {
		return getZoneId("America/La_Paz");
	}


	public static ZoneId americaLima() {
		return getZoneId("America/Lima");
	}


	public static ZoneId americaLosAngeles() {
		return getZoneId("America/Los_Angeles");
	}


	public static ZoneId americaLouisville() {
		return getZoneId("America/Louisville");
	}


	public static ZoneId americaLowerPrinces() {
		return getZoneId("America/Lower_Princes");
	}


	public static ZoneId americaMaceio() {
		return getZoneId("America/Maceio");
	}


	public static ZoneId americaManagua() {
		return getZoneId("America/Managua");
	}


	public static ZoneId americaManaus() {
		return getZoneId("America/Manaus");
	}


	public static ZoneId americaMarigot() {
		return getZoneId("America/Marigot");
	}


	public static ZoneId americaMartinique() {
		return getZoneId("America/Martinique");
	}


	public static ZoneId americaMatamoros() {
		return getZoneId("America/Matamoros");
	}


	public static ZoneId americaMazatlan() {
		return getZoneId("America/Mazatlan");
	}


	public static ZoneId americaMendoza() {
		return getZoneId("America/Mendoza");
	}


	public static ZoneId americaMenominee() {
		return getZoneId("America/Menominee");
	}


	public static ZoneId americaMerida() {
		return getZoneId("America/Merida");
	}


	public static ZoneId americaMetlakatla() {
		return getZoneId("America/Metlakatla");
	}


	public static ZoneId americaMexicoCity() {
		return getZoneId("America/Mexico_City");
	}


	public static ZoneId americaMiquelon() {
		return getZoneId("America/Miquelon");
	}


	public static ZoneId americaMoncton() {
		return getZoneId("America/Moncton");
	}


	public static ZoneId americaMonterrey() {
		return getZoneId("America/Monterrey");
	}


	public static ZoneId americaMontevideo() {
		return getZoneId("America/Montevideo");
	}


	public static ZoneId americaMontreal() {
		return getZoneId("America/Montreal");
	}


	public static ZoneId americaMontserrat() {
		return getZoneId("America/Montserrat");
	}


	public static ZoneId americaNassau() {
		return getZoneId("America/Nassau");
	}


	public static ZoneId americaNewYork() {
		return getZoneId("America/New_York");
	}


	public static ZoneId americaNipigon() {
		return getZoneId("America/Nipigon");
	}


	public static ZoneId americaNome() {
		return getZoneId("America/Nome");
	}


	public static ZoneId americaNoronha() {
		return getZoneId("America/Noronha");
	}


	public static ZoneId americaNorthDakotaBeulah() {
		return getZoneId("America/North_Dakota/Beulah");
	}


	public static ZoneId americaNorthDakotaCenter() {
		return getZoneId("America/North_Dakota/Center");
	}


	public static ZoneId americaNorthDakotaNewSalem() {
		return getZoneId("America/North_Dakota/New_Salem");
	}


	public static ZoneId americaOjinaga() {
		return getZoneId("America/Ojinaga");
	}


	public static ZoneId americaPanama() {
		return getZoneId("America/Panama");
	}


	public static ZoneId americaPangnirtung() {
		return getZoneId("America/Pangnirtung");
	}


	public static ZoneId americaParamaribo() {
		return getZoneId("America/Paramaribo");
	}


	public static ZoneId americaPhoenix() {
		return getZoneId("America/Phoenix");
	}


	public static ZoneId americaPortAuPrince() {
		return getZoneId("America/Port-au-Prince");
	}


	public static ZoneId americaPortOfSpain() {
		return getZoneId("America/Port_of_Spain");
	}


	public static ZoneId americaPortoAcre() {
		return getZoneId("America/Porto_Acre");
	}


	public static ZoneId americaPortoVelho() {
		return getZoneId("America/Porto_Velho");
	}


	public static ZoneId americaPuertoRico() {
		return getZoneId("America/Puerto_Rico");
	}


	public static ZoneId americaRainyRiver() {
		return getZoneId("America/Rainy_River");
	}


	public static ZoneId americaRankinInlet() {
		return getZoneId("America/Rankin_Inlet");
	}


	public static ZoneId americaRecife() {
		return getZoneId("America/Recife");
	}


	public static ZoneId americaRegina() {
		return getZoneId("America/Regina");
	}


	public static ZoneId americaResolute() {
		return getZoneId("America/Resolute");
	}


	public static ZoneId americaRioBranco() {
		return getZoneId("America/Rio_Branco");
	}


	public static ZoneId americaRosario() {
		return getZoneId("America/Rosario");
	}


	public static ZoneId americaSantaIsabel() {
		return getZoneId("America/Santa_Isabel");
	}


	public static ZoneId americaSantarem() {
		return getZoneId("America/Santarem");
	}


	public static ZoneId americaSantiago() {
		return getZoneId("America/Santiago");
	}


	public static ZoneId americaSantoDomingo() {
		return getZoneId("America/Santo_Domingo");
	}


	public static ZoneId americaSaoPaulo() {
		return getZoneId("America/Sao_Paulo");
	}


	public static ZoneId americaScoresbysund() {
		return getZoneId("America/Scoresbysund");
	}


	public static ZoneId americaShiprock() {
		return getZoneId("America/Shiprock");
	}


	public static ZoneId americaSitka() {
		return getZoneId("America/Sitka");
	}


	public static ZoneId americaStBarthelemy() {
		return getZoneId("America/St_Barthelemy");
	}


	public static ZoneId americaStJohns() {
		return getZoneId("America/St_Johns");
	}


	public static ZoneId americaStKitts() {
		return getZoneId("America/St_Kitts");
	}


	public static ZoneId americaStLucia() {
		return getZoneId("America/St_Lucia");
	}


	public static ZoneId americaStThomas() {
		return getZoneId("America/St_Thomas");
	}


	public static ZoneId americaStVincent() {
		return getZoneId("America/St_Vincent");
	}


	public static ZoneId americaSwiftCurrent() {
		return getZoneId("America/Swift_Current");
	}


	public static ZoneId americaTegucigalpa() {
		return getZoneId("America/Tegucigalpa");
	}


	public static ZoneId americaThule() {
		return getZoneId("America/Thule");
	}


	public static ZoneId americaThunderBay() {
		return getZoneId("America/Thunder_Bay");
	}


	public static ZoneId americaTijuana() {
		return getZoneId("America/Tijuana");
	}


	public static ZoneId americaToronto() {
		return getZoneId("America/Toronto");
	}


	public static ZoneId americaTortola() {
		return getZoneId("America/Tortola");
	}


	public static ZoneId americaVancouver() {
		return getZoneId("America/Vancouver");
	}


	public static ZoneId americaVirgin() {
		return getZoneId("America/Virgin");
	}


	public static ZoneId americaWhitehorse() {
		return getZoneId("America/Whitehorse");
	}


	public static ZoneId americaWinnipeg() {
		return getZoneId("America/Winnipeg");
	}


	public static ZoneId americaYakutat() {
		return getZoneId("America/Yakutat");
	}


	public static ZoneId americaYellowknife() {
		return getZoneId("America/Yellowknife");
	}


	public static ZoneId antarcticaCasey() {
		return getZoneId("Antarctica/Casey");
	}


	public static ZoneId antarcticaDavis() {
		return getZoneId("Antarctica/Davis");
	}


	public static ZoneId antarcticaDumontDUrville() {
		return getZoneId("Antarctica/DumontDUrville");
	}


	public static ZoneId antarcticaMacquarie() {
		return getZoneId("Antarctica/Macquarie");
	}


	public static ZoneId antarcticaMawson() {
		return getZoneId("Antarctica/Mawson");
	}


	public static ZoneId antarcticaMcMurdo() {
		return getZoneId("Antarctica/McMurdo");
	}


	public static ZoneId antarcticaPalmer() {
		return getZoneId("Antarctica/Palmer");
	}


	public static ZoneId antarcticaRothera() {
		return getZoneId("Antarctica/Rothera");
	}


	public static ZoneId antarcticaSouthPole() {
		return getZoneId("Antarctica/South_Pole");
	}


	public static ZoneId antarcticaSyowa() {
		return getZoneId("Antarctica/Syowa");
	}


	public static ZoneId antarcticaTroll() {
		return getZoneId("Antarctica/Troll");
	}


	public static ZoneId antarcticaVostok() {
		return getZoneId("Antarctica/Vostok");
	}


	public static ZoneId arcticLongyearbyen() {
		return getZoneId("Arctic/Longyearbyen");
	}


	public static ZoneId asiaAden() {
		return getZoneId("Asia/Aden");
	}


	public static ZoneId asiaAlmaty() {
		return getZoneId("Asia/Almaty");
	}


	public static ZoneId asiaAmman() {
		return getZoneId("Asia/Amman");
	}


	public static ZoneId asiaAnadyr() {
		return getZoneId("Asia/Anadyr");
	}


	public static ZoneId asiaAqtau() {
		return getZoneId("Asia/Aqtau");
	}


	public static ZoneId asiaAqtobe() {
		return getZoneId("Asia/Aqtobe");
	}


	public static ZoneId asiaAshgabat() {
		return getZoneId("Asia/Ashgabat");
	}


	public static ZoneId asiaAshkhabad() {
		return getZoneId("Asia/Ashkhabad");
	}


	public static ZoneId asiaBaghdad() {
		return getZoneId("Asia/Baghdad");
	}


	public static ZoneId asiaBahrain() {
		return getZoneId("Asia/Bahrain");
	}


	public static ZoneId asiaBaku() {
		return getZoneId("Asia/Baku");
	}


	public static ZoneId asiaBangkok() {
		return getZoneId("Asia/Bangkok");
	}


	public static ZoneId asiaBeirut() {
		return getZoneId("Asia/Beirut");
	}


	public static ZoneId asiaBishkek() {
		return getZoneId("Asia/Bishkek");
	}


	public static ZoneId asiaBrunei() {
		return getZoneId("Asia/Brunei");
	}


	public static ZoneId asiaCalcutta() {
		return getZoneId("Asia/Calcutta");
	}


	public static ZoneId asiaChita() {
		return getZoneId("Asia/Chita");
	}


	public static ZoneId asiaChoibalsan() {
		return getZoneId("Asia/Choibalsan");
	}


	public static ZoneId asiaChongqing() {
		return getZoneId("Asia/Chongqing");
	}


	public static ZoneId asiaChungking() {
		return getZoneId("Asia/Chungking");
	}


	public static ZoneId asiaColombo() {
		return getZoneId("Asia/Colombo");
	}


	public static ZoneId asiaDacca() {
		return getZoneId("Asia/Dacca");
	}


	public static ZoneId asiaDamascus() {
		return getZoneId("Asia/Damascus");
	}


	public static ZoneId asiaDhaka() {
		return getZoneId("Asia/Dhaka");
	}


	public static ZoneId asiaDili() {
		return getZoneId("Asia/Dili");
	}


	public static ZoneId asiaDubai() {
		return getZoneId("Asia/Dubai");
	}


	public static ZoneId asiaDushanbe() {
		return getZoneId("Asia/Dushanbe");
	}


	public static ZoneId asiaGaza() {
		return getZoneId("Asia/Gaza");
	}


	public static ZoneId asiaHarbin() {
		return getZoneId("Asia/Harbin");
	}


	public static ZoneId asiaHebron() {
		return getZoneId("Asia/Hebron");
	}


	public static ZoneId asiaHoChiMinh() {
		return getZoneId("Asia/Ho_Chi_Minh");
	}


	public static ZoneId asiaHongKong() {
		return getZoneId("Asia/Hong_Kong");
	}


	public static ZoneId asiaHovd() {
		return getZoneId("Asia/Hovd");
	}


	public static ZoneId asiaIrkutsk() {
		return getZoneId("Asia/Irkutsk");
	}


	public static ZoneId asiaIstanbul() {
		return getZoneId("Asia/Istanbul");
	}


	public static ZoneId asiaJakarta() {
		return getZoneId("Asia/Jakarta");
	}


	public static ZoneId asiaJayapura() {
		return getZoneId("Asia/Jayapura");
	}


	public static ZoneId asiaJerusalem() {
		return getZoneId("Asia/Jerusalem");
	}


	public static ZoneId asiaKabul() {
		return getZoneId("Asia/Kabul");
	}


	public static ZoneId asiaKamchatka() {
		return getZoneId("Asia/Kamchatka");
	}


	public static ZoneId asiaKarachi() {
		return getZoneId("Asia/Karachi");
	}


	public static ZoneId asiaKashgar() {
		return getZoneId("Asia/Kashgar");
	}


	public static ZoneId asiaKathmandu() {
		return getZoneId("Asia/Kathmandu");
	}


	public static ZoneId asiaKatmandu() {
		return getZoneId("Asia/Katmandu");
	}


	public static ZoneId asiaKhandyga() {
		return getZoneId("Asia/Khandyga");
	}


	public static ZoneId asiaKolkata() {
		return getZoneId("Asia/Kolkata");
	}


	public static ZoneId asiaKrasnoyarsk() {
		return getZoneId("Asia/Krasnoyarsk");
	}


	public static ZoneId asiaKualaLumpur() {
		return getZoneId("Asia/Kuala_Lumpur");
	}


	public static ZoneId asiaKuching() {
		return getZoneId("Asia/Kuching");
	}


	public static ZoneId asiaKuwait() {
		return getZoneId("Asia/Kuwait");
	}


	public static ZoneId asiaMacao() {
		return getZoneId("Asia/Macao");
	}


	public static ZoneId asiaMacau() {
		return getZoneId("Asia/Macau");
	}


	public static ZoneId asiaMagadan() {
		return getZoneId("Asia/Magadan");
	}


	public static ZoneId asiaMakassar() {
		return getZoneId("Asia/Makassar");
	}


	public static ZoneId asiaManila() {
		return getZoneId("Asia/Manila");
	}


	public static ZoneId asiaMuscat() {
		return getZoneId("Asia/Muscat");
	}


	public static ZoneId asiaNicosia() {
		return getZoneId("Asia/Nicosia");
	}


	public static ZoneId asiaNovokuznetsk() {
		return getZoneId("Asia/Novokuznetsk");
	}


	public static ZoneId asiaNovosibirsk() {
		return getZoneId("Asia/Novosibirsk");
	}


	public static ZoneId asiaOmsk() {
		return getZoneId("Asia/Omsk");
	}


	public static ZoneId asiaOral() {
		return getZoneId("Asia/Oral");
	}


	public static ZoneId asiaPhnomPenh() {
		return getZoneId("Asia/Phnom_Penh");
	}


	public static ZoneId asiaPontianak() {
		return getZoneId("Asia/Pontianak");
	}


	public static ZoneId asiaPyongyang() {
		return getZoneId("Asia/Pyongyang");
	}


	public static ZoneId asiaQatar() {
		return getZoneId("Asia/Qatar");
	}


	public static ZoneId asiaQyzylorda() {
		return getZoneId("Asia/Qyzylorda");
	}


	public static ZoneId asiaRangoon() {
		return getZoneId("Asia/Rangoon");
	}


	public static ZoneId asiaRiyadh() {
		return getZoneId("Asia/Riyadh");
	}


	public static ZoneId asiaSaigon() {
		return getZoneId("Asia/Saigon");
	}


	public static ZoneId asiaSakhalin() {
		return getZoneId("Asia/Sakhalin");
	}


	public static ZoneId asiaSamarkand() {
		return getZoneId("Asia/Samarkand");
	}


	public static ZoneId asiaSeoul() {
		return getZoneId("Asia/Seoul");
	}


	public static ZoneId asiaShanghai() {
		return getZoneId("Asia/Shanghai");
	}


	public static ZoneId asiaSingapore() {
		return getZoneId("Asia/Singapore");
	}


	public static ZoneId asiaSrednekolymsk() {
		return getZoneId("Asia/Srednekolymsk");
	}


	public static ZoneId asiaTaipei() {
		return getZoneId("Asia/Taipei");
	}


	public static ZoneId asiaTashkent() {
		return getZoneId("Asia/Tashkent");
	}


	public static ZoneId asiaTbilisi() {
		return getZoneId("Asia/Tbilisi");
	}


	public static ZoneId asiaTehran() {
		return getZoneId("Asia/Tehran");
	}


	public static ZoneId asiaTelAviv() {
		return getZoneId("Asia/Tel_Aviv");
	}


	public static ZoneId asiaThimbu() {
		return getZoneId("Asia/Thimbu");
	}


	public static ZoneId asiaThimphu() {
		return getZoneId("Asia/Thimphu");
	}


	public static ZoneId asiaTokyo() {
		return getZoneId("Asia/Tokyo");
	}


	public static ZoneId asiaUjungPandang() {
		return getZoneId("Asia/Ujung_Pandang");
	}


	public static ZoneId asiaUlaanbaatar() {
		return getZoneId("Asia/Ulaanbaatar");
	}


	public static ZoneId asiaUlanBator() {
		return getZoneId("Asia/Ulan_Bator");
	}


	public static ZoneId asiaUrumqi() {
		return getZoneId("Asia/Urumqi");
	}


	public static ZoneId asiaUstNera() {
		return getZoneId("Asia/Ust-Nera");
	}


	public static ZoneId asiaVientiane() {
		return getZoneId("Asia/Vientiane");
	}


	public static ZoneId asiaVladivostok() {
		return getZoneId("Asia/Vladivostok");
	}


	public static ZoneId asiaYakutsk() {
		return getZoneId("Asia/Yakutsk");
	}


	public static ZoneId asiaYekaterinburg() {
		return getZoneId("Asia/Yekaterinburg");
	}


	public static ZoneId asiaYerevan() {
		return getZoneId("Asia/Yerevan");
	}


	public static ZoneId atlanticAzores() {
		return getZoneId("Atlantic/Azores");
	}


	public static ZoneId atlanticBermuda() {
		return getZoneId("Atlantic/Bermuda");
	}


	public static ZoneId atlanticCanary() {
		return getZoneId("Atlantic/Canary");
	}


	public static ZoneId atlanticCapeVerde() {
		return getZoneId("Atlantic/Cape_Verde");
	}


	public static ZoneId atlanticFaeroe() {
		return getZoneId("Atlantic/Faeroe");
	}


	public static ZoneId atlanticFaroe() {
		return getZoneId("Atlantic/Faroe");
	}


	public static ZoneId atlanticJanMayen() {
		return getZoneId("Atlantic/Jan_Mayen");
	}


	public static ZoneId atlanticMadeira() {
		return getZoneId("Atlantic/Madeira");
	}


	public static ZoneId atlanticReykjavik() {
		return getZoneId("Atlantic/Reykjavik");
	}


	public static ZoneId atlanticSouthGeorgia() {
		return getZoneId("Atlantic/South_Georgia");
	}


	public static ZoneId atlanticStHelena() {
		return getZoneId("Atlantic/St_Helena");
	}


	public static ZoneId atlanticStanley() {
		return getZoneId("Atlantic/Stanley");
	}


	public static ZoneId australiaACT() {
		return getZoneId("Australia/ACT");
	}


	public static ZoneId australiaAdelaide() {
		return getZoneId("Australia/Adelaide");
	}


	public static ZoneId australiaBrisbane() {
		return getZoneId("Australia/Brisbane");
	}


	public static ZoneId australiaBrokenHill() {
		return getZoneId("Australia/Broken_Hill");
	}


	public static ZoneId australiaCanberra() {
		return getZoneId("Australia/Canberra");
	}


	public static ZoneId australiaCurrie() {
		return getZoneId("Australia/Currie");
	}


	public static ZoneId australiaDarwin() {
		return getZoneId("Australia/Darwin");
	}


	public static ZoneId australiaEucla() {
		return getZoneId("Australia/Eucla");
	}


	public static ZoneId australiaHobart() {
		return getZoneId("Australia/Hobart");
	}


	public static ZoneId australiaLHI() {
		return getZoneId("Australia/LHI");
	}


	public static ZoneId australiaLindeman() {
		return getZoneId("Australia/Lindeman");
	}


	public static ZoneId australiaLordHowe() {
		return getZoneId("Australia/Lord_Howe");
	}


	public static ZoneId australiaMelbourne() {
		return getZoneId("Australia/Melbourne");
	}


	public static ZoneId australiaNSW() {
		return getZoneId("Australia/NSW");
	}


	public static ZoneId australiaNorth() {
		return getZoneId("Australia/North");
	}


	public static ZoneId australiaPerth() {
		return getZoneId("Australia/Perth");
	}


	public static ZoneId australiaQueensland() {
		return getZoneId("Australia/Queensland");
	}


	public static ZoneId australiaSouth() {
		return getZoneId("Australia/South");
	}


	public static ZoneId australiaSydney() {
		return getZoneId("Australia/Sydney");
	}


	public static ZoneId australiaTasmania() {
		return getZoneId("Australia/Tasmania");
	}


	public static ZoneId australiaVictoria() {
		return getZoneId("Australia/Victoria");
	}


	public static ZoneId australiaWest() {
		return getZoneId("Australia/West");
	}


	public static ZoneId australiaYancowinna() {
		return getZoneId("Australia/Yancowinna");
	}


	public static ZoneId brazilAcre() {
		return getZoneId("Brazil/Acre");
	}


	public static ZoneId brazilDeNoronha() {
		return getZoneId("Brazil/DeNoronha");
	}


	public static ZoneId brazilEast() {
		return getZoneId("Brazil/East");
	}


	public static ZoneId brazilWest() {
		return getZoneId("Brazil/West");
	}


	public static ZoneId cet() {
		return getZoneId("CET");
	}


	public static ZoneId cst6cdt() {
		return getZoneId("CST6CDT");
	}


	public static ZoneId canadaAtlantic() {
		return getZoneId("Canada/Atlantic");
	}


	public static ZoneId canadaCentral() {
		return getZoneId("Canada/Central");
	}


	public static ZoneId canadaEastSaskatchewan() {
		return getZoneId("Canada/East-Saskatchewan");
	}


	public static ZoneId canadaEastern() {
		return getZoneId("Canada/Eastern");
	}


	public static ZoneId canadaMountain() {
		return getZoneId("Canada/Mountain");
	}


	public static ZoneId canadaNewfoundland() {
		return getZoneId("Canada/Newfoundland");
	}


	public static ZoneId canadaPacific() {
		return getZoneId("Canada/Pacific");
	}


	public static ZoneId canadaSaskatchewan() {
		return getZoneId("Canada/Saskatchewan");
	}


	public static ZoneId canadaYukon() {
		return getZoneId("Canada/Yukon");
	}


	public static ZoneId chileContinental() {
		return getZoneId("Chile/Continental");
	}


	public static ZoneId chileEasterIsland() {
		return getZoneId("Chile/EasterIsland");
	}


	public static ZoneId cuba() {
		return getZoneId("Cuba");
	}


	public static ZoneId eet() {
		return getZoneId("EET");
	}


	public static ZoneId est5edt() {
		return getZoneId("EST5EDT");
	}


	public static ZoneId egypt() {
		return getZoneId("Egypt");
	}


	public static ZoneId eire() {
		return getZoneId("Eire");
	}


	public static ZoneId etcGmt() {
		return getZoneId("Etc/GMT");
	}


	public static ZoneId etcGmtPlus0() {
		return getZoneId("Etc/GMT+0");
	}


	public static ZoneId etcGmtPlus1() {
		return getZoneId("Etc/GMT+1");
	}


	public static ZoneId etcGmtPlus10() {
		return getZoneId("Etc/GMT+10");
	}


	public static ZoneId etcGmtPlus11() {
		return getZoneId("Etc/GMT+11");
	}


	public static ZoneId etcGmtPlus12() {
		return getZoneId("Etc/GMT+12");
	}


	public static ZoneId etcGmtPlus2() {
		return getZoneId("Etc/GMT+2");
	}


	public static ZoneId etcGmtPlus3() {
		return getZoneId("Etc/GMT+3");
	}


	public static ZoneId etcGmtPlus4() {
		return getZoneId("Etc/GMT+4");
	}


	public static ZoneId etcGmtPlus5() {
		return getZoneId("Etc/GMT+5");
	}


	public static ZoneId etcGmtPlus6() {
		return getZoneId("Etc/GMT+6");
	}


	public static ZoneId etcGmtPlus7() {
		return getZoneId("Etc/GMT+7");
	}


	public static ZoneId etcGmtPlus8() {
		return getZoneId("Etc/GMT+8");
	}


	public static ZoneId etcGmtPlus9() {
		return getZoneId("Etc/GMT+9");
	}


	public static ZoneId etcGmtMinus0() {
		return getZoneId("Etc/GMT-0");
	}


	public static ZoneId etcGmtMinus1() {
		return getZoneId("Etc/GMT-1");
	}


	public static ZoneId etcGmtMinus10() {
		return getZoneId("Etc/GMT-10");
	}


	public static ZoneId etcGmtMinus11() {
		return getZoneId("Etc/GMT-11");
	}


	public static ZoneId etcGmtMinus12() {
		return getZoneId("Etc/GMT-12");
	}


	public static ZoneId etcGmtMinus13() {
		return getZoneId("Etc/GMT-13");
	}


	public static ZoneId etcGmtMinus14() {
		return getZoneId("Etc/GMT-14");
	}


	public static ZoneId etcGmtMinus2() {
		return getZoneId("Etc/GMT-2");
	}


	public static ZoneId etcGmtMinus3() {
		return getZoneId("Etc/GMT-3");
	}


	public static ZoneId etcGmtMinus4() {
		return getZoneId("Etc/GMT-4");
	}


	public static ZoneId etcGmtMinus5() {
		return getZoneId("Etc/GMT-5");
	}


	public static ZoneId etcGmtMinus6() {
		return getZoneId("Etc/GMT-6");
	}


	public static ZoneId etcGmtMinus7() {
		return getZoneId("Etc/GMT-7");
	}


	public static ZoneId etcGmtMinus8() {
		return getZoneId("Etc/GMT-8");
	}


	public static ZoneId etcGmtMinus9() {
		return getZoneId("Etc/GMT-9");
	}


	public static ZoneId etcGmt0() {
		return getZoneId("Etc/GMT0");
	}


	public static ZoneId etcGreenwich() {
		return getZoneId("Etc/Greenwich");
	}


	public static ZoneId etcUCT() {
		return getZoneId("Etc/UCT");
	}


	public static ZoneId etcUTC() {
		return getZoneId("Etc/UTC");
	}


	public static ZoneId etcUniversal() {
		return getZoneId("Etc/Universal");
	}


	public static ZoneId etcZulu() {
		return getZoneId("Etc/Zulu");
	}


	public static ZoneId europeAmsterdam() {
		return getZoneId("Europe/Amsterdam");
	}


	public static ZoneId europeAndorra() {
		return getZoneId("Europe/Andorra");
	}


	public static ZoneId europeAthens() {
		return getZoneId("Europe/Athens");
	}


	public static ZoneId europeBelfast() {
		return getZoneId("Europe/Belfast");
	}


	public static ZoneId europeBelgrade() {
		return getZoneId("Europe/Belgrade");
	}


	public static ZoneId europeBerlin() {
		return getZoneId("Europe/Berlin");
	}


	public static ZoneId europeBratislava() {
		return getZoneId("Europe/Bratislava");
	}


	public static ZoneId europeBrussels() {
		return getZoneId("Europe/Brussels");
	}


	public static ZoneId europeBucharest() {
		return getZoneId("Europe/Bucharest");
	}


	public static ZoneId europeBudapest() {
		return getZoneId("Europe/Budapest");
	}


	public static ZoneId europeBusingen() {
		return getZoneId("Europe/Busingen");
	}


	public static ZoneId europeChisinau() {
		return getZoneId("Europe/Chisinau");
	}


	public static ZoneId europeCopenhagen() {
		return getZoneId("Europe/Copenhagen");
	}


	public static ZoneId europeDublin() {
		return getZoneId("Europe/Dublin");
	}


	public static ZoneId europeGibraltar() {
		return getZoneId("Europe/Gibraltar");
	}


	public static ZoneId europeGuernsey() {
		return getZoneId("Europe/Guernsey");
	}


	public static ZoneId europeHelsinki() {
		return getZoneId("Europe/Helsinki");
	}


	public static ZoneId europeIsleOfMan() {
		return getZoneId("Europe/Isle_of_Man");
	}


	public static ZoneId europeIstanbul() {
		return getZoneId("Europe/Istanbul");
	}


	public static ZoneId europeJersey() {
		return getZoneId("Europe/Jersey");
	}


	public static ZoneId europeKaliningrad() {
		return getZoneId("Europe/Kaliningrad");
	}


	public static ZoneId europeKiev() {
		return getZoneId("Europe/Kiev");
	}


	public static ZoneId europeLisbon() {
		return getZoneId("Europe/Lisbon");
	}


	public static ZoneId europeLjubljana() {
		return getZoneId("Europe/Ljubljana");
	}


	public static ZoneId europeLondon() {
		return getZoneId("Europe/London");
	}


	public static ZoneId europeLuxembourg() {
		return getZoneId("Europe/Luxembourg");
	}


	public static ZoneId europeMadrid() {
		return getZoneId("Europe/Madrid");
	}


	public static ZoneId europeMalta() {
		return getZoneId("Europe/Malta");
	}


	public static ZoneId europeMariehamn() {
		return getZoneId("Europe/Mariehamn");
	}


	public static ZoneId europeMinsk() {
		return getZoneId("Europe/Minsk");
	}


	public static ZoneId europeMonaco() {
		return getZoneId("Europe/Monaco");
	}


	public static ZoneId europeMoscow() {
		return getZoneId("Europe/Moscow");
	}


	public static ZoneId europeNicosia() {
		return getZoneId("Europe/Nicosia");
	}


	public static ZoneId europeOslo() {
		return getZoneId("Europe/Oslo");
	}


	public static ZoneId europeParis() {
		return getZoneId("Europe/Paris");
	}


	public static ZoneId europePodgorica() {
		return getZoneId("Europe/Podgorica");
	}


	public static ZoneId europePrague() {
		return getZoneId("Europe/Prague");
	}


	public static ZoneId europeRiga() {
		return getZoneId("Europe/Riga");
	}


	public static ZoneId europeRome() {
		return getZoneId("Europe/Rome");
	}


	public static ZoneId europeSamara() {
		return getZoneId("Europe/Samara");
	}


	public static ZoneId europeSanMarino() {
		return getZoneId("Europe/San_Marino");
	}


	public static ZoneId europeSarajevo() {
		return getZoneId("Europe/Sarajevo");
	}


	public static ZoneId europeSimferopol() {
		return getZoneId("Europe/Simferopol");
	}


	public static ZoneId europeSkopje() {
		return getZoneId("Europe/Skopje");
	}


	public static ZoneId europeSofia() {
		return getZoneId("Europe/Sofia");
	}


	public static ZoneId europeStockholm() {
		return getZoneId("Europe/Stockholm");
	}


	public static ZoneId europeTallinn() {
		return getZoneId("Europe/Tallinn");
	}


	public static ZoneId europeTirane() {
		return getZoneId("Europe/Tirane");
	}


	public static ZoneId europeTiraspol() {
		return getZoneId("Europe/Tiraspol");
	}


	public static ZoneId europeUzhgorod() {
		return getZoneId("Europe/Uzhgorod");
	}


	public static ZoneId europeVaduz() {
		return getZoneId("Europe/Vaduz");
	}


	public static ZoneId europeVatican() {
		return getZoneId("Europe/Vatican");
	}


	public static ZoneId europeVienna() {
		return getZoneId("Europe/Vienna");
	}


	public static ZoneId europeVilnius() {
		return getZoneId("Europe/Vilnius");
	}


	public static ZoneId europeVolgograd() {
		return getZoneId("Europe/Volgograd");
	}


	public static ZoneId europeWarsaw() {
		return getZoneId("Europe/Warsaw");
	}


	public static ZoneId europeZagreb() {
		return getZoneId("Europe/Zagreb");
	}


	public static ZoneId europeZaporozhye() {
		return getZoneId("Europe/Zaporozhye");
	}


	public static ZoneId europeZurich() {
		return getZoneId("Europe/Zurich");
	}


	public static ZoneId gb() {
		return getZoneId("GB");
	}


	public static ZoneId gbEire() {
		return getZoneId("GB-Eire");
	}


	public static ZoneId gmt() {
		return getZoneId("GMT");
	}


	public static ZoneId gmt0() {
		return getZoneId("GMT0");
	}


	public static ZoneId greenwich() {
		return getZoneId("Greenwich");
	}


	public static ZoneId hongkong() {
		return getZoneId("Hongkong");
	}


	public static ZoneId iceland() {
		return getZoneId("Iceland");
	}


	public static ZoneId indianAntananarivo() {
		return getZoneId("Indian/Antananarivo");
	}


	public static ZoneId indianChagos() {
		return getZoneId("Indian/Chagos");
	}


	public static ZoneId indianChristmas() {
		return getZoneId("Indian/Christmas");
	}


	public static ZoneId indianCocos() {
		return getZoneId("Indian/Cocos");
	}


	public static ZoneId indianComoro() {
		return getZoneId("Indian/Comoro");
	}


	public static ZoneId indianKerguelen() {
		return getZoneId("Indian/Kerguelen");
	}


	public static ZoneId indianMahe() {
		return getZoneId("Indian/Mahe");
	}


	public static ZoneId indianMaldives() {
		return getZoneId("Indian/Maldives");
	}


	public static ZoneId indianMauritius() {
		return getZoneId("Indian/Mauritius");
	}


	public static ZoneId indianMayotte() {
		return getZoneId("Indian/Mayotte");
	}


	public static ZoneId indianReunion() {
		return getZoneId("Indian/Reunion");
	}


	public static ZoneId iran() {
		return getZoneId("Iran");
	}


	public static ZoneId israel() {
		return getZoneId("Israel");
	}


	public static ZoneId jamaica() {
		return getZoneId("Jamaica");
	}


	public static ZoneId japan() {
		return getZoneId("Japan");
	}


	public static ZoneId kwajalein() {
		return getZoneId("Kwajalein");
	}


	public static ZoneId libya() {
		return getZoneId("Libya");
	}


	public static ZoneId met() {
		return getZoneId("MET");
	}


	public static ZoneId mst7mdt() {
		return getZoneId("MST7MDT");
	}


	public static ZoneId mexicoBajaNorte() {
		return getZoneId("Mexico/BajaNorte");
	}


	public static ZoneId mexicoBajaSur() {
		return getZoneId("Mexico/BajaSur");
	}


	public static ZoneId mexicoGeneral() {
		return getZoneId("Mexico/General");
	}


	public static ZoneId nz() {
		return getZoneId("NZ");
	}


	public static ZoneId nzChat() {
		return getZoneId("NZ-CHAT");
	}


	public static ZoneId navajo() {
		return getZoneId("Navajo");
	}


	public static ZoneId prc() {
		return getZoneId("PRC");
	}


	public static ZoneId pst8pdt() {
		return getZoneId("PST8PDT");
	}


	public static ZoneId pacificApia() {
		return getZoneId("Pacific/Apia");
	}


	public static ZoneId pacificAuckland() {
		return getZoneId("Pacific/Auckland");
	}


	public static ZoneId pacificBougainville() {
		return getZoneId("Pacific/Bougainville");
	}


	public static ZoneId pacificChatham() {
		return getZoneId("Pacific/Chatham");
	}


	public static ZoneId pacificChuuk() {
		return getZoneId("Pacific/Chuuk");
	}


	public static ZoneId pacificEaster() {
		return getZoneId("Pacific/Easter");
	}


	public static ZoneId pacificEfate() {
		return getZoneId("Pacific/Efate");
	}


	public static ZoneId pacificEnderbury() {
		return getZoneId("Pacific/Enderbury");
	}


	public static ZoneId pacificFakaofo() {
		return getZoneId("Pacific/Fakaofo");
	}


	public static ZoneId pacificFiji() {
		return getZoneId("Pacific/Fiji");
	}


	public static ZoneId pacificFunafuti() {
		return getZoneId("Pacific/Funafuti");
	}


	public static ZoneId pacificGalapagos() {
		return getZoneId("Pacific/Galapagos");
	}


	public static ZoneId pacificGambier() {
		return getZoneId("Pacific/Gambier");
	}


	public static ZoneId pacificGuadalcanal() {
		return getZoneId("Pacific/Guadalcanal");
	}


	public static ZoneId pacificGuam() {
		return getZoneId("Pacific/Guam");
	}


	public static ZoneId pacificHonolulu() {
		return getZoneId("Pacific/Honolulu");
	}


	public static ZoneId pacificJohnston() {
		return getZoneId("Pacific/Johnston");
	}


	public static ZoneId pacificKiritimati() {
		return getZoneId("Pacific/Kiritimati");
	}


	public static ZoneId pacificKosrae() {
		return getZoneId("Pacific/Kosrae");
	}


	public static ZoneId pacificKwajalein() {
		return getZoneId("Pacific/Kwajalein");
	}


	public static ZoneId pacificMajuro() {
		return getZoneId("Pacific/Majuro");
	}


	public static ZoneId pacificMarquesas() {
		return getZoneId("Pacific/Marquesas");
	}


	public static ZoneId pacificMidway() {
		return getZoneId("Pacific/Midway");
	}


	public static ZoneId pacificNauru() {
		return getZoneId("Pacific/Nauru");
	}


	public static ZoneId pacificNiue() {
		return getZoneId("Pacific/Niue");
	}


	public static ZoneId pacificNorfolk() {
		return getZoneId("Pacific/Norfolk");
	}


	public static ZoneId pacificNoumea() {
		return getZoneId("Pacific/Noumea");
	}


	public static ZoneId pacificPagoPago() {
		return getZoneId("Pacific/Pago_Pago");
	}


	public static ZoneId pacificPalau() {
		return getZoneId("Pacific/Palau");
	}


	public static ZoneId pacificPitcairn() {
		return getZoneId("Pacific/Pitcairn");
	}


	public static ZoneId pacificPohnpei() {
		return getZoneId("Pacific/Pohnpei");
	}


	public static ZoneId pacificPonape() {
		return getZoneId("Pacific/Ponape");
	}


	public static ZoneId pacificPortMoresby() {
		return getZoneId("Pacific/Port_Moresby");
	}


	public static ZoneId pacificRarotonga() {
		return getZoneId("Pacific/Rarotonga");
	}


	public static ZoneId pacificSaipan() {
		return getZoneId("Pacific/Saipan");
	}


	public static ZoneId pacificSamoa() {
		return getZoneId("Pacific/Samoa");
	}


	public static ZoneId pacificTahiti() {
		return getZoneId("Pacific/Tahiti");
	}


	public static ZoneId pacificTarawa() {
		return getZoneId("Pacific/Tarawa");
	}


	public static ZoneId pacificTongatapu() {
		return getZoneId("Pacific/Tongatapu");
	}


	public static ZoneId pacificTruk() {
		return getZoneId("Pacific/Truk");
	}


	public static ZoneId pacificWake() {
		return getZoneId("Pacific/Wake");
	}


	public static ZoneId pacificWallis() {
		return getZoneId("Pacific/Wallis");
	}


	public static ZoneId pacificYap() {
		return getZoneId("Pacific/Yap");
	}


	public static ZoneId poland() {
		return getZoneId("Poland");
	}


	public static ZoneId portugal() {
		return getZoneId("Portugal");
	}


	public static ZoneId rok() {
		return getZoneId("ROK");
	}


	public static ZoneId singapore() {
		return getZoneId("Singapore");
	}


	public static ZoneId systemVAST4() {
		return getZoneId("SystemV/AST4");
	}


	public static ZoneId systemVAST4ADT() {
		return getZoneId("SystemV/AST4ADT");
	}


	public static ZoneId systemVCST6() {
		return getZoneId("SystemV/CST6");
	}


	public static ZoneId systemVCST6CDT() {
		return getZoneId("SystemV/CST6CDT");
	}


	public static ZoneId systemVEST5() {
		return getZoneId("SystemV/EST5");
	}


	public static ZoneId systemVEST5EDT() {
		return getZoneId("SystemV/EST5EDT");
	}


	public static ZoneId systemVHST10() {
		return getZoneId("SystemV/HST10");
	}


	public static ZoneId systemVMST7() {
		return getZoneId("SystemV/MST7");
	}


	public static ZoneId systemVMST7MDT() {
		return getZoneId("SystemV/MST7MDT");
	}


	public static ZoneId systemVPST8() {
		return getZoneId("SystemV/PST8");
	}


	public static ZoneId systemVPST8PDT() {
		return getZoneId("SystemV/PST8PDT");
	}


	public static ZoneId systemVYST9() {
		return getZoneId("SystemV/YST9");
	}


	public static ZoneId systemVYST9YDT() {
		return getZoneId("SystemV/YST9YDT");
	}


	public static ZoneId turkey() {
		return getZoneId("Turkey");
	}


	public static ZoneId uct() {
		return getZoneId("UCT");
	}


	public static ZoneId usAlaska() {
		return getZoneId("US/Alaska");
	}


	public static ZoneId usAleutian() {
		return getZoneId("US/Aleutian");
	}


	public static ZoneId usArizona() {
		return getZoneId("US/Arizona");
	}


	public static ZoneId usCentral() {
		return getZoneId("US/Central");
	}


	public static ZoneId usEastIndiana() {
		return getZoneId("US/East-Indiana");
	}


	public static ZoneId usEastern() {
		return getZoneId("US/Eastern");
	}


	public static ZoneId usHawaii() {
		return getZoneId("US/Hawaii");
	}


	public static ZoneId usIndianaStarke() {
		return getZoneId("US/Indiana-Starke");
	}


	public static ZoneId usMichigan() {
		return getZoneId("US/Michigan");
	}


	public static ZoneId usMountain() {
		return getZoneId("US/Mountain");
	}


	public static ZoneId usPacific() {
		return getZoneId("US/Pacific");
	}


	public static ZoneId usPacificNew() {
		return getZoneId("US/Pacific-New");
	}


	public static ZoneId usSamoa() {
		return getZoneId("US/Samoa");
	}


	public static ZoneId utc() {
		return getZoneId("UTC");
	}


	public static ZoneId universal() {
		return getZoneId("Universal");
	}


	public static ZoneId wSu() {
		return getZoneId("W-SU");
	}


	public static ZoneId wet() {
		return getZoneId("WET");
	}


	public static ZoneId zulu() {
		return getZoneId("Zulu");
	}

}
