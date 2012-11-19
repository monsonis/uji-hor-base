var server = sinon.fakeServer.create();
server.autoRespond = true;
server.fakeHTTPMethods = true;

var json_semestredetalle = {
	success : true,
	data : [ {
		id : 1,
		fecha_fin : "23/12/2012 00:00:00",
		tipo_estudioId : "12C",
		nombreSemestre : "Primer semestre",
		nombreTipoEstudio : "Primer i segon cicle",
		numero_semanas : "14",
		fecha_inicio : "20/09/2012 00:00:00",
		fecha_examenes_fin : "24/01/2013 00:00:00",
		fecha_examenes_inicio : "24/12/2012 00:00:00"
	}, {
		id : 3,
		fecha_fin : "24/12/2012 00:00:00",
		tipo_estudioId : "G",
		nombreSemestre : "Primer semestre",
		nombreTipoEstudio : "Grados",
		numero_semanas : "18",
		fecha_inicio : "20/09/2012 00:00:00",
		fecha_examenes_fin : "24/01/2013 00:00:00",
		fecha_examenes_inicio : "25/12/2012 00:00:00"
	}, {
		id : 2,
		fecha_fin : "18/05/2013 00:00:00",
		tipo_estudioId : "12C",
		nombreSemestre : "Segon semestre",
		nombreTipoEstudio : "Primer i segon cicle",
		numero_semanas : "17",
		fecha_inicio : "25/01/2013 00:00:00",
		fecha_examenes_fin : "15/07/2013 00:00:00",
		fecha_examenes_inicio : "19/05/2013 00:00:00"
	}, {
		id : 4,
		fecha_fin : "08/06/2013 00:00:00",
		tipo_estudioId : "G",
		nombreSemestre : "Segon semestre",
		nombreTipoEstudio : "Grados",
		numero_semanas : "20",
		fecha_inicio : "25/01/2013 00:00:00",
		fecha_examenes_fin : "15/07/2013 00:00:00",
		fecha_examenes_inicio : "15/05/2013 00:00:00"
	} ]
};

server.respondWith("GET", /\/hor\/rest\/semestredetalle?.*/, [ 200, {
	"Content-Type" : "application/json"
}, JSON.stringify(json_semestredetalle) ]);

var json_centro = {
	success : true,
	data : [ {
		nombre : "Incorrecte",
		id : 0
	}, {
		nombre : "Facultat de CiÃ¨ncies Humanes i Socials",
		id : 2
	}, {
		nombre : "Facultat de CiÃ¨ncies JurÃ­diques i EconÃ²miques",
		id : 3
	}, {
		nombre : "Escola Superior de Tecnologia i CiÃ¨ncies Experimentals",
		id : 4
	}, {
		nombre : "Facultat de CiÃ¨ncies de la Salut",
		id : 2922
	} ]
};

server.respondWith("GET", /\/hor\/rest\/centro?.*/, [ 200, {
	"Content-Type" : "application/json"
}, JSON.stringify(json_centro) ]);

var json_estudios = {
	success : true,
	data : [ {
		nombre : "Llicenciatura en Psicologia",
		id : 17
	}, {
		nombre : "Grau en Psicologia",
		id : 219
	}, {
		nombre : "Grau en Medicina",
		id : 229
	}, {
		nombre : "Grau en Infermeria",
		id : 230
	} ]
};

server.respondWith("GET", /\/hor\/rest\/estudio?.*centroId=.*/, [ 200, {
	"Content-Type" : "application/json"
}, JSON.stringify(json_estudios) ]);

server.respondWith("GET", /\/hor\/rest\/estudio?.*/, [ 200, {
	"Content-Type" : "application/json"
}, JSON.stringify(json_estudios) ]);