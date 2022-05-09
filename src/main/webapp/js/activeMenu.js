/**
 * Created by SonamPC on 21-Mar-18.
 */

$(document).ready(function () {

    //region === Menu Heighlighting ===
    let url = window.location.pathname + window.location.search;
    let menuLink = $('.sidebar-menu').children('.menu')
        .children('.has-sub').children('ul')
        .children('li').children('a');

    $.each(menuLink, function () {
        renderLink($(this));
    });

    let firstLevelLink = $('.sidebar-menu')
        .children('.menu').children('.sidebar-item').children('a');
    $.each(firstLevelLink, function () {
        renderLink($(this));
    });
    $.each(firstLevelLink, function () {
        renderLink($(this));
    });

    function renderLink($this) {
        if ($this.attr('href') === url) {

            $this.closest('.submenu').addClass('active');
            $this.closest('.sidebar-item').addClass("active");
            $this.closest('.submenu-item').addClass("active");

            $this.css({
                "color": "#0b90ef", "font-weight": "bold"
            });
        }
    }
});

