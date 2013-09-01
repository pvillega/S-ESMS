package com.perevillega.sesms.support

import play.api.libs.json.{JsValue, Json}
import scala.util.Random


/**
 * Represents a Country
 * @param name Name of the country
 * @param code 2 letter "code" of the country
 */
case class Country(name: String, code: String)


object Country {
  /**
   * Support object to Country Player to Json
   * Needs to be above classes that require it for the macro to work
   */
  implicit val fmt = Json.format[Country]

  /**
   * Gets a random country from all he valid countries
   * @return a country
   */
  def getRandomCountry(): Country = Random.shuffle(allCountries).head

  /**
   * Returns a list with all countries ordered alphabetically
   * @return a list with all countries ordered alphabetically
   */
  def getAllCountries() = allCountries


  /**
   * Constructs a Country from its Json version
   * @param json Json value that contains a Country
   * @return a Country object built from the json parameter
   */
  def fromJson(json: JsValue): Country = Json.fromJson[Country](json).get

  /**
   * Creates a json version of the Country
   * @param country the Country to turn into json
   * @return a JsValue with the Country
   */
  def toJson(country: Country): JsValue = Json.toJson(country)


  // list of all countries, obtained from https://gist.github.com/Keeguon/2310008
  private lazy val allCountries: List[Country] = List(Country("Afghanistan", "AF"),
    Country("Åland Islands", "AX"),
    Country("Albania", "AL"),
    Country("Algeria", "DZ"),
    Country("American Samoa", "AS"),
    Country("AndorrA", "AD"),
    Country("Angola", "AO"),
    Country("Anguilla", "AI"),
    Country("Antarctica", "AQ"),
    Country("Antigua and Barbuda", "AG"),
    Country("Argentina", "AR"),
    Country("Armenia", "AM"),
    Country("Aruba", "AW"),
    Country("Australia", "AU"),
    Country("Austria", "AT"),
    Country("Azerbaijan", "AZ"),
    Country("Bahamas", "BS"),
    Country("Bahrain", "BH"),
    Country("Bangladesh", "BD"),
    Country("Barbados", "BB"),
    Country("Belarus", "BY"),
    Country("Belgium", "BE"),
    Country("Belize", "BZ"),
    Country("Benin", "BJ"),
    Country("Bermuda", "BM"),
    Country("Bhutan", "BT"),
    Country("Bolivia", "BO"),
    Country("Bosnia and Herzegovina", "BA"),
    Country("Botswana", "BW"),
    Country("Bouvet Island", "BV"),
    Country("Brazil", "BR"),
    Country("British Indian Ocean Territory", "IO"),
    Country("Brunei Darussalam", "BN"),
    Country("Bulgaria", "BG"),
    Country("Burkina Faso", "BF"),
    Country("Burundi", "BI"),
    Country("Cambodia", "KH"),
    Country("Cameroon", "CM"),
    Country("Canada", "CA"),
    Country("Cape Verde", "CV"),
    Country("Cayman Islands", "KY"),
    Country("Central African Republic", "CF"),
    Country("Chad", "TD"),
    Country("Chile", "CL"),
    Country("China", "CN"),
    Country("Christmas Island", "CX"),
    Country("Cocos (Keeling) Islands", "CC"),
    Country("Colombia", "CO"),
    Country("Comoros", "KM"),
    Country("Congo", "CG"),
    Country("Congo, The Democratic Republic of the", "CD"),
    Country("Cook Islands", "CK"),
    Country("Costa Rica", "CR"),
    Country("Cote D'Ivoire", "CI"),
    Country("Croatia", "HR"),
    Country("Cuba", "CU"),
    Country("Cyprus", "CY"),
    Country("Czech Republic", "CZ"),
    Country("Denmark", "DK"),
    Country("Djibouti", "DJ"),
    Country("Dominica", "DM"),
    Country("Dominican Republic", "DO"),
    Country("Ecuador", "EC"),
    Country("Egypt", "EG"),
    Country("El Salvador", "SV"),
    Country("Equatorial Guinea", "GQ"),
    Country("Eritrea", "ER"),
    Country("Estonia", "EE"),
    Country("Ethiopia", "ET"),
    Country("Falkland Islands (Malvinas)", "FK"),
    Country("Faroe Islands", "FO"),
    Country("Fiji", "FJ"),
    Country("Finland", "FI"),
    Country("France", "FR"),
    Country("French Guiana", "GF"),
    Country("French Polynesia", "PF"),
    Country("French Southern Territories", "TF"),
    Country("Gabon", "GA"),
    Country("Gambia", "GM"),
    Country("Georgia", "GE"),
    Country("Germany", "DE"),
    Country("Ghana", "GH"),
    Country("Gibraltar", "GI"),
    Country("Greece", "GR"),
    Country("Greenland", "GL"),
    Country("Grenada", "GD"),
    Country("Guadeloupe", "GP"),
    Country("Guam", "GU"),
    Country("Guatemala", "GT"),
    Country("Guernsey", "GG"),
    Country("Guinea", "GN"),
    Country("Guinea-Bissau", "GW"),
    Country("Guyana", "GY"),
    Country("Haiti", "HT"),
    Country("Heard Island and Mcdonald Islands", "HM"),
    Country("Holy See (Vatican City State)", "VA"),
    Country("Honduras", "HN"),
    Country("Hong Kong", "HK"),
    Country("Hungary", "HU"),
    Country("Iceland", "IS"),
    Country("India", "IN"),
    Country("Indonesia", "ID"),
    Country("Iran, Islamic Republic Of", "IR"),
    Country("Iraq", "IQ"),
    Country("Ireland", "IE"),
    Country("Isle of Man", "IM"),
    Country("Israel", "IL"),
    Country("Italy", "IT"),
    Country("Jamaica", "JM"),
    Country("Japan", "JP"),
    Country("Jersey", "JE"),
    Country("Jordan", "JO"),
    Country("Kazakhstan", "KZ"),
    Country("Kenya", "KE"),
    Country("Kiribati", "KI"),
    Country("Korea, Democratic People's Republic of", "KP"),
    Country("Korea, Republic of", "KR"),
    Country("Kuwait", "KW"),
    Country("Kyrgyzstan", "KG"),
    Country("Lao People's Democratic Republic", "LA"),
    Country("Latvia", "LV"),
    Country("Lebanon", "LB"),
    Country("Lesotho", "LS"),
    Country("Liberia", "LR"),
    Country("Libyan Arab Jamahiriya", "LY"),
    Country("Liechtenstein", "LI"),
    Country("Lithuania", "LT"),
    Country("Luxembourg", "LU"),
    Country("Macao", "MO"),
    Country("Macedonia, The Former Yugoslav Republic of", "MK"),
    Country("Madagascar", "MG"),
    Country("Malawi", "MW"),
    Country("Malaysia", "MY"),
    Country("Maldives", "MV"),
    Country("Mali", "ML"),
    Country("Malta", "MT"),
    Country("Marshall Islands", "MH"),
    Country("Martinique", "MQ"),
    Country("Mauritania", "MR"),
    Country("Mauritius", "MU"),
    Country("Mayotte", "YT"),
    Country("Mexico", "MX"),
    Country("Micronesia, Federated States of", "FM"),
    Country("Moldova, Republic of", "MD"),
    Country("Monaco", "MC"),
    Country("Mongolia", "MN"),
    Country("Montserrat", "MS"),
    Country("Morocco", "MA"),
    Country("Mozambique", "MZ"),
    Country("Myanmar", "MM"),
    Country("Namibia", "NA"),
    Country("Nauru", "NR"),
    Country("Nepal", "NP"),
    Country("Netherlands", "NL"),
    Country("Netherlands Antilles", "AN"),
    Country("New Caledonia", "NC"),
    Country("New Zealand", "NZ"),
    Country("Nicaragua", "NI"),
    Country("Niger", "NE"),
    Country("Nigeria", "NG"),
    Country("Niue", "NU"),
    Country("Norfolk Island", "NF"),
    Country("Northern Mariana Islands", "MP"),
    Country("Norway", "NO"),
    Country("Oman", "OM"),
    Country("Pakistan", "PK"),
    Country("Palau", "PW"),
    Country("Palestinian Territory, Occupied", "PS"),
    Country("Panama", "PA"),
    Country("Papua New Guinea", "PG"),
    Country("Paraguay", "PY"),
    Country("Peru", "PE"),
    Country("Philippines", "PH"),
    Country("Pitcairn", "PN"),
    Country("Poland", "PL"),
    Country("Portugal", "PT"),
    Country("Puerto Rico", "PR"),
    Country("Qatar", "QA"),
    Country("Reunion", "RE"),
    Country("Romania", "RO"),
    Country("Russian Federation", "RU"),
    Country("RWANDA", "RW"),
    Country("Saint Helena", "SH"),
    Country("Saint Kitts and Nevis", "KN"),
    Country("Saint Lucia", "LC"),
    Country("Saint Pierre and Miquelon", "PM"),
    Country("Saint Vincent and the Grenadines", "VC"),
    Country("Samoa", "WS"),
    Country("San Marino", "SM"),
    Country("Sao Tome and Principe", "ST"),
    Country("Saudi Arabia", "SA"),
    Country("Senegal", "SN"),
    Country("Serbia and Montenegro", "CS"),
    Country("Seychelles", "SC"),
    Country("Sierra Leone", "SL"),
    Country("Singapore", "SG"),
    Country("Slovakia", "SK"),
    Country("Slovenia", "SI"),
    Country("Solomon Islands", "SB"),
    Country("Somalia", "SO"),
    Country("South Africa", "ZA"),
    Country("South Georgia and the South Sandwich Islands", "GS"),
    Country("Spain", "ES"),
    Country("Sri Lanka", "LK"),
    Country("Sudan", "SD"),
    Country("Suriname", "SR"),
    Country("Svalbard and Jan Mayen", "SJ"),
    Country("Swaziland", "SZ"),
    Country("Sweden", "SE"),
    Country("Switzerland", "CH"),
    Country("Syrian Arab Republic", "SY"),
    Country("Taiwan, Province of China", "TW"),
    Country("Tajikistan", "TJ"),
    Country("Tanzania, United Republic of", "TZ"),
    Country("Thailand", "TH"),
    Country("Timor-Leste", "TL"),
    Country("Togo", "TG"),
    Country("Tokelau", "TK"),
    Country("Tonga", "TO"),
    Country("Trinidad and Tobago", "TT"),
    Country("Tunisia", "TN"),
    Country("Turkey", "TR"),
    Country("Turkmenistan", "TM"),
    Country("Turks and Caicos Islands", "TC"),
    Country("Tuvalu", "TV"),
    Country("Uganda", "UG"),
    Country("Ukraine", "UA"),
    Country("United Arab Emirates", "AE"),
    Country("United Kingdom", "GB"),
    Country("United States", "US"),
    Country("United States Minor Outlying Islands", "UM"),
    Country("Uruguay", "UY"),
    Country("Uzbekistan", "UZ"),
    Country("Vanuatu", "VU"),
    Country("Venezuela", "VE"),
    Country("Vietnam", "VN"),
    Country("Virgin Islands, British", "VG"),
    Country("Virgin Islands, U.S.", "VI"),
    Country("Wallis and Futuna", "WF"),
    Country("Western Sahara", "EH"),
    Country("Yemen", "YE"),
    Country("Zambia", "ZM"),
    Country("Zimbabwe", "ZW"))

}

