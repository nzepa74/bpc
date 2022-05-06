permission = (function () {
    "use strict";
    let form = $('#permissionFormId');
    let isSubmitted = false;
    let permissionTable = $('#permissionTable');

    function _baseURL() {
        return 'api/permission/';
    }


    function onChangeRole() {
        $('#roleId').on('change', function () {
            let roleId = $('#roleId').find('option:selected').val();
            if (roleId === '') {
                permissionTable.find('tbody').empty();
                permissionTable.find('tbody').empty();
            } else {
                permissionTable.find('tbody').empty();
                permissionTable.find('tbody').empty();
                getScreens(roleId);
            }
        });
    }

    function getScreens(roleId) {
        $.ajax({
            url: _baseURL() + 'getScreens',
            type: 'GET',
            data: {roleId: roleId},
            success: function (res) {
                for (let i in res) {
                    let viewAllowed = res[i].viewAllowed === 'Y' ? 'checked=""' : '';
                    let isEditAccessAllowed = res[i].editAllowed === 'Y' ? 'checked=""' : '';
                    let isDeleteAccessAllowed = res[i].deleteAllowed === 'Y' ? 'checked=""' : '';
                    let isSaveAccessAllowed = res[i].saveAllowed === 'Y' ? 'checked=""' : '';
                    let permissionId = res[i].permissionId == null ? '' : res[i].permissionId;
                    let row = '<tr>\
                        <td><input type="hidden" id="id" name="permissionListDTOS[' + i + '].permissionId" class="form-control form-control-sm"  value="' + permissionId + '"><input type="hidden" id="screenId" name="permissionListDTOS[' + i + '].screenId" class=" form-control form-control-sm" readonly="true" value="' + res[i].screenId + '">' + res[i].screenId + '</td>\
                        <td><input type="hidden" id="screenName" class="form-control form-control-sm" readonly="true" value="' + res[i].screenName + '">' + res[i].screenName + '</td>\
                        <td><div class="text-center">\
                        <input type="checkbox" ' + viewAllowed + ' class="checkView" id="checkMe">\
                        <input type="hidden" id="permission" name="permissionListDTOS[' + i + '].viewAllowed"/></div></td>\
                        <td><div class="text-center">\
                        <input type="checkbox" ' + isSaveAccessAllowed + ' class="checkAdd" id="checkMe">\
                        <input type="hidden" id="permission" name="permissionListDTOS[' + i + '].saveAllowed"/> \
                        </div></td>\
                        <td><div class="text-center">\
                        <input type="checkbox" ' + isEditAccessAllowed + ' class="checkEdit" id="checkMe">\
                        <input type="hidden" id="permission" name="permissionListDTOS[' + i + '].editAllowed"/> \
                        </div></td>\
                        <td><div class="text-center">\
                        <input type="checkbox" ' + isDeleteAccessAllowed + ' class="checkDelete" id="checkMe">\
                        <input type="hidden" id="permission" name="permissionListDTOS[' + i + '].deleteAllowed"/> \
                        </div></td>\
                    </tr>';

                    let tableBody = $('#permissionTable tbody');
                    let notFound = $(tableBody).find('td').first().html();
                    if (notFound === 'No data available in table')
                        $(tableBody).find('tr').remove();
                    $('#permissionTable').find('tbody').append(row);
                }
            }
        });
    }

    function tickCheckbox() {
        $('#permissionTable').on('click', 'input[type="checkbox"]', function () {
            let _this = $(this);
            if (_this.hasClass('checkDelete')) {
                if (_this.prop('checked'))
                    _this.parents('td').siblings('td').find('input[type="checkbox"]').prop('checked', _this.prop('checked'));
            }
            if (_this.hasClass('checkEdit')) {
                if (_this.prop('checked'))
                    _this.parents('td').siblings('td').find('input[type="checkbox"]').not('.checkDelete').prop('checked', _this.prop('checked'));
                else
                    _this.parents('td').siblings('td').find('.checkDelete').prop('checked', _this.prop('checked'));
            }
            if (_this.hasClass('checkAdd')) {
                if (_this.prop('checked'))
                    _this.parents('td').siblings('td').find('input[type="checkbox"]').not('.checkDelete, .checkEdit').prop('checked', _this.prop('checked'));
                else
                    _this.parents('td').siblings('td').find('.checkDelete, .checkEdit').prop('checked', _this.prop('checked'));
            }
            if (_this.hasClass('checkView')) {
                if (!_this.prop('checked'))
                    _this.parents('td').siblings('td').find('.checkDelete, .checkEdit, .checkAdd').prop('checked', _this.prop('checked'));
            }
        });
    }

    function savePermission() {
        $('.btnSavePermission').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                $(form).find('input[type="checkbox"]').each(function () {
                    if ($(this).is(':checked')) {
                        $(this).next('input[type="hidden"]').val('Y');
                    } else {
                        $(this).next('input[type="hidden"]').val('N');
                    }
                });

                let audio = new Audio('assets/sounds/info/1.mp3');
                audio.play();
                $.confirm({
                    title: 'Confirmation',
                    content: 'Are you sure you want to save now?',
                    type: 'orange',
                    typeAnimated: true,
                    icon: 'fa fa-question',
                    buttons: {
                        close: {
                            text: 'No, Cancel',
                        },
                        confirm: {
                            text: 'Yes, Sure',
                            btnClass: 'btn-red',
                            action: function () {
                                $.ajax({
                                    url: _baseURL() + 'savePermission',
                                    type: 'POST',
                                    data: $(form).serializeArray(),
                                    success: function (res) {
                                        if (res.status === 1) {
                                            $('#permissionTable tbody').empty();
                                            getScreens($('#roleId').val());
                                            successMsg(res.text);
                                        } else {
                                            warningMsg(res.text);
                                        }
                                    }
                                });
                            }
                        },
                    }
                });
            }
        });
    }


    return {
        onChangeRole: onChangeRole,
        tickCheckbox: tickCheckbox,
        savePermission: savePermission,
    }
})();

$(document).ready(function () {
    permission.onChangeRole();
    permission.tickCheckbox();
    permission.savePermission();
});