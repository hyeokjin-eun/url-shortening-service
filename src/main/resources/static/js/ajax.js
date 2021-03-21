(() => {
    function originUrlChangeRequest () {
        let url = document.querySelector(`#origin-url`).value;

        if (url === null || url === 'undefined' || url === ``) {
            alert(`변경할 URL을 입력해주세요.`);
            return;
        }

        $.ajax({
            url: `/rest/url`,
            dataType: `json`,
            method: `post`,
            contentType: `application/json`,
            data: JSON.stringify({
                url : url
            }),
            success: function(response) {
                if (response == null || response.resultCode != `OK`) {
                    alert(`URL 변환 실패`);
                    return;
                }

                console.log(response.data.url);
                document.querySelector(`#encoding-url`).value = response.data.url;
                alert(`URL 변환 성공`)
            },
            error: function(error) {
                console.log(error);
                alert(`URL 변환 실패`);
            }
        });
    }

    function encodingUrlMoveRequest () {
        location.href = document.querySelector(`#encoding-url`).value;
    }


    document.querySelector("#origin-url-request").addEventListener(`click`, () => {
        originUrlChangeRequest();
    });

    document.querySelector("#encoding-url-request").addEventListener(`click`, () => {
        encodingUrlMoveRequest();
    });
})();