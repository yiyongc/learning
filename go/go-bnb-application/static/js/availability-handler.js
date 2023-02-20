function addRoomAvailabilityHandler(roomId, csrfToken) {
    document.getElementById("check-availability-btn").addEventListener("click", function () {
        let html = `
                    <form id="check-availability-form" action="" method="post" novalidate class="needs-validation">
                        <div id="reservation-dates-modal" class="row w-100">
                            <div class="col">
                                <input disabled required class="form-control" type="text" id="start" name="start" placeholder="Arrival Date">
                            </div>
                            <div class="col">
                                <input disabled required class="form-control" type="text" id="end" name="end" placeholder="Departure Date">
                            </div>
                        </div>
                    </form>
                `;

        attention.custom({
            title: "Choose your dates",
            msg: html,
            willOpen: () => {
                const elem = document.getElementById("reservation-dates-modal");
                const rp = new DateRangePicker(elem, {
                    format: "yyyy-mm-dd",
                    showOnFocus: true,
                    minDate: new Date(),
                });
            },
            didOpen: () => {
                document.getElementById("start").removeAttribute("disabled");
                document.getElementById("end").removeAttribute("disabled");
            },
            callback: async function (result) {
                let form = document.getElementById("check-availability-form");
                let formData = new FormData(form);
                formData.append("csrf_token", "" + csrfToken);
                formData.append("room_id", "" + roomId);

                response = await fetch("/search-availability-json", {
                    method: "post",
                    body: formData
                });

                console.log(response);
                let data = await response.json();
                if (data.ok) {
                    attention.custom({
                        icon: "success",
                        msg: "<p>Room is available!</p>"
                            + '<p><a href="/book-room?id='
                            + data.room_id
                            + '&s='
                            + data.start_date
                            + '&e='
                            + data.end_date
                            + '" class="btn btn-primary">'
                            + "Book now!</a></p>",
                        showConfirmButton: false,
                    });
                } else {
                    attention.error({
                        msg: "Room is unavailable"
                    });
                }
            }
        });
    })
}