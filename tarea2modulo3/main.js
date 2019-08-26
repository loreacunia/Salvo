

Vue.component('partido', {
    props: ['nombre', 'mes', 'dia', 'horario', 'lugar', 'url'],
    template: `
    <li class="list-group-item border-dark d-flex text-center align-items-center">
        <span class="flex-shrink text-center">{{  dia + mes + ' ' + horario }}</span><span class="w-50">{{ nombre }}
        </span>
        <span>  <a v-bind:href="url">{{ lugar }}</a>
        </span>
    
    </li>
    `,
})
var app = new Vue({
    el: '#app',
    data: {
        partidos: [{
            nombre: "u1 vs u4",
            mes: "/9",
            horario: "9:30 am",
            lugar: " AJ Katzenmaier",
            dia: "01",
            url: "https://www.google.com/maps/d/embed?mid=1QnaR8ef5aC4r4C255jypOHspc2YYhLka"



        },

        {
            nombre: "u2 vs u3",
            mes: "/9",
            horario: "1:30 pm",
            lugar: " Greenbay",
            dia: "01",
            url: "https://www.google.com/maps/d/embed?mid=1hZNOyJTQ-rF98ZLixGy8m37H5mvs-3fN"

        },

        {
            nombre: "u5 vs u6",
            mes: "/9",
            horario: "9:30 am",
            lugar: " Howard A Yeager",
            dia: "08",
            url: "https://www.google.com/maps/d/embed?mid=1wbhmRjZGOF9eRDn6oIIciuXqQd2gi_Yg"

        },
        {
            nombre: "u6 vs u1",
            mes: "/9",
            horario: "1:30 pm",
            lugar: " Marjorie P Hart",
            dia: "08",
            url: "https://www.google.com/maps/d/embed?mid=1SID7QdfDWObUWMXQOsC6PyJM3FKPEW9K"

        },
        {
            nombre: "u2 vs u4",
            mes: "/9",
            horario: "9:30 am",
            lugar: " North",
            dia: "15",
            url: "https://www.google.com/maps/d/embed?mid=1KZCKiaP9yNRrGkWK52RmnKaF9gy8M8xB"
        },
        {
            nombre: "u3 vs u5",
            mes: "/9",
            horario: "1:30 pm",
            lugar: " AJ Katzenmaier",
            dia: "15",
            url: "https://www.google.com/maps/d/embed?mid=1QnaR8ef5aC4r4C255jypOHspc2YYhLka"

        },
        {
            nombre: "u1 vs u3",
            mes: "/9",
            horario: "9:30 am",
            lugar: " South",
            dia: "22",
            url: "https://www.google.com/maps/d/embed?mid=1xAgVyYoWCHYLd_WxuoHsMA16JfzNQASg"
        },
        {
            nombre: "u2 vs u6",
            mes: "/9",
            horario: "1:30 pm",
            lugar: " Howard A Yeager",
            dia: "22",
            url: "https://www.google.com/maps/d/embed?mid=1wbhmRjZGOF9eRDn6oIIciuXqQd2gi_Yg"
        },
        {
            nombre: "u4 vs u5",
            mes: "/9",
            horario: "9:30 am",
            lugar: " Greenbay",
            dia: "29",
            url: "https://www.google.com/maps/d/embed?mid=1hZNOyJTQ-rF98ZLixGy8m37H5mvs-3fN"

        },
        {
            nombre: "u2 vs u5",
            mes: "/10",
            horario: "9:30 am",
            lugar: "Marjorie P Hart	 ",
            dia: "06",
            url: "https://www.google.com/maps/d/embed?mid=1SID7QdfDWObUWMXQOsC6PyJM3FKPEW9K"
        },
        {
            nombre: "u1 vs u6",
            mes: "/10",
            horario: "1:00 am",
            lugar: "South ",
            dia: "08",
            url: "https://www.google.com/maps/d/embed?mid=1xAgVyYoWCHYLd_WxuoHsMA16JfzNQASg"
        },
        {
            nombre: "u3 vs u4",
            mes: "/10",
            horario: "9:30 am",
            lugar: "Howard A Yeager",
            dia: "08",
            url:  "https://www.google.com/maps/d/embed?mid=1wbhmRjZGOF9eRDn6oIIciuXqQd2gi_Yg"
        },
        {
            nombre: "u5 vs u1",
            mes: "/10",
            horario: "1:00 am",
            lugar: "Greenbay",
            dia: "08",
            url:"https://www.google.com/maps/d/embed?mid=1hZNOyJTQ-rF98ZLixGy8m37H5mvs-3fN"
        },
        {
            nombre: "u6 vs u3",
            mes: "/10",
            horario: "9:30 am",
            lugar: "North",
            dia: "20",
            url:"https://www.google.com/maps/d/embed?mid=1KZCKiaP9yNRrGkWK52RmnKaF9gy8M8xB"
        },
        {
            nombre: "u6 vs u3",
            mes: "/10",
            horario: "9:30 am",
            lugar: "North",
            dia: "20",
            url:"https://www.google.com/maps/d/embed?mid=1KZCKiaP9yNRrGkWK52RmnKaF9gy8M8xB"
        }
    ]
    }
})





$(".fixed-action-btn").click(function () {
    $('.fixed-action-btn').floatingActionButton();
    var data = "<p>   Please email us at: <a href='mailto:nysl@chisoccer.org'>nysl@chisoccer.org</a></p>  We will reply to your email as soon as we can."
    Swal.fire({
        title: " <i class='small material-icons'>drafts</i> Contact ",
        html: data

    })

});



