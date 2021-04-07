<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="smartclinicApp.visit.home.createOrEditLabel" data-cy="VisitCreateUpdateHeading">إنشاء او تعديل زيارة</h2>
        <hr />
        <div>
          <div class="form-group row" v-if="visit.id">
            <label for="example-email-input" class="col-md-2 col-form-label form-control-label">الكود</label>
            <div class="col-md-10">
              <base-input type="text" name="id" v-model="visit.id" readonly />
            </div>
          </div>
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" for="visit-patient">المريض</label>
            <div class="col-md-10">
              <select class="form-control" id="visit-patient" data-cy="patient" name="patient" v-model="visit.patient" disabled>
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="visit.patient && patientOption.id === visit.patient.id ? visit.patient : patientOption"
                  v-for="patientOption in patients"
                  :key="patientOption.id"
                >
                  {{ patientOption.name }}
                </option>
              </select>
              <div v-if="$v.visit.patient.$invalid">
                <small class="form-text text-danger" v-if="!$v.visit.patient.required"> المريض مطلوب </small>
              </div>
            </div>
          </div>
          <div class="form-group row" style="margin-top: 3rem">
            <label class="col-md-2 col-form-label form-control-label" for="status">الحالة</label>
            <div class="col-md-10">
              <select class="form-control" aria-placeholder="الحالة" id="status" data-cy="status" name="status" v-model="visit.status">
                <option v-bind:value="null"></option>
                <option v-bind:value="visit.status && 'Reserved' === visit.status ? visit.status : 'Reserved'" :key="'Reserved'">
                  حجز
                </option>
                <option v-bind:value="visit.status && 'Waiting' === visit.status ? visit.status : 'Waiting'" :key="'Waiting'">
                  أنتظار
                </option>
                <option v-bind:value="visit.status && 'Served' === visit.status ? visit.status : 'Served'" :key="'Served'">تم كشف</option>
              </select>
              <div v-if="$v.visit.status.$invalid">
                <small class="form-text text-danger" v-if="!$v.visit.status.required"> الحالة مطلوب </small>
              </div>
            </div>
          </div>
          <div class="form-group row" style="margin-top: 3rem">
            <label class="col-md-2 col-form-label form-control-label" for="visit-type">نوع الزيارة</label>
            <div class="col-md-10">
              <select
                class="form-control"
                aria-placeholder="نوع الزيارة"
                id="visit-type"
                data-cy="visitType"
                name="visitType"
                v-model="visit.visitType"
              >
                <option v-bind:value="null"></option>
                <option v-bind:value="visit.visitType && 'Reveal' === visit.visitType ? visit.visitType : 'Reveal'" :key="'Reveal'">
                  كشف
                </option>
                <option v-bind:value="visit.visitType && 'Repeat' === visit.visitType ? visit.visitType : 'Repeat'" :key="'Repeat'">
                  أعادة
                </option>
                <option v-bind:value="visit.visitType && 'Other' === visit.visitType ? visit.visitType : 'Other'" :key="'Other'">
                  أخرى
                </option>
              </select>
              <div v-if="$v.visit.visitType.$invalid">
                <small class="form-text text-danger" v-if="!$v.visit.visitType.required"> نوع الزيارة مطلوب </small>
              </div>
            </div>
          </div>

          <div class="form-group row" style="margin-top: 3rem">
            <label class="col-md-2 col-form-label form-control-label" for="visit-type">السعر</label>
            <div class="col-md-10">
              <base-input
                type="number"
                name="السعر"
                :rules="{ required: true, max: 7 }"
                data-cy="cost"
                alternative
                v-model="$v.visit.cost.$model"
              />
            </div>
          </div>
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" for="visit-type">الوصف</label>
            <div class="col-md-10">
              <base-input
                type="textarea"
                name="الوصف"
                :rules="{ max: 1000 }"
                data-cy="description"
                alternative
                v-model="$v.visit.description.$model"
              />
            </div>
          </div>
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" for="visit-type">الادوية الموصوفة</label>
            <div class="col-md-10">
              <base-input
                type="textarea"
                name="الادوىة الموصوفة"
                :rules="{ max: 1000 }"
                data-cy="medicine"
                alternative
                v-model="$v.visit.medicine.$model"
              />
            </div>
          </div>
          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" for="visit-type">ملاحظات</label>
            <div class="col-md-10">
              <base-input type="textarea" name="ملاحظات" :rules="{ max: 1000 }" data-cy="note" alternative v-model="$v.visit.note.$model" />
            </div>
          </div>

          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" for="visit-type">تاريخ الزيارة</label>
            <div class="d-flex col-md-4">
              <!-- <base-input
                data-cy="dateOfVisit"
                type="datetime-local"
                name="تاريخ الزيارة"
                label="تاريخ الزيارة"
                placeholder="تاريخ الزيارة"
                alternative
                :value="convertDateTimeFromServer($v.visit.dateOfVisit.$model)"
                @change="updateZonedDateTimeField('dateOfVisit', $event)"
                :rules="{ required: true, max: 30 }"
              /> -->

              <datetime v-model="value1" @change="updateZonedDateTimeField('dateOfVisit', $event)" type="date">
                <template slot="button-cancel"> ألغاء </template>
                <template slot="button-confirm"> تاكيد </template>
              </datetime>
              <div v-if="$v.value1.$anyDirty && $v.value1.$invalid">
                <small class="form-text text-danger" v-if="!$v.value1.required"> تاريخ الزيارة مطلوب </small>
              </div>
            </div>
            <div class="col-md-2">
              <select class="form-control" aria-placeholder="الساعه" id="hour" data-cy="hour" name="hour" v-model="hour">
                <option v-bind:value="null" disabled></option>
                <option v-bind:value="hour && 1 === hour ? hour : 1" :key="1">01</option>
                <option v-bind:value="hour && 2 === hour ? hour : 2" :key="2">02</option>
                <option v-bind:value="hour && 3 === hour ? hour : 3" :key="3">03</option>
                <option v-bind:value="hour && 4 === hour ? hour : 4" :key="4">04</option>
                <option v-bind:value="hour && 5 === hour ? hour : 5" :key="5">05</option>
                <option v-bind:value="hour && 6 === hour ? hour : 6" :key="6">06</option>
                <option v-bind:value="hour && 7 === hour ? hour : 7" :key="7">07</option>
                <option v-bind:value="hour && 8 === hour ? hour : 8" :key="8">08</option>
                <option v-bind:value="hour && 9 === hour ? hour : 9" :key="9">09</option>
                <option v-bind:value="hour && 10 === hour ? hour : 10" :key="10">10</option>
                <option v-bind:value="hour && 11 === hour ? hour : 11" :key="11">11</option>
                <option v-bind:value="hour && 12 === hour ? hour : 12" :key="12">12</option>
              </select>
              <div v-if="$v.hour.$invalid">
                <small class="form-text text-danger" v-if="!$v.hour.required"> تاريخ الزيارة مطلوب </small>
              </div>
            </div>
            <div class="col-md-2">
              <select class="form-control" aria-placeholder="الدقيقة" id="minute" data-cy="minute" name="minute" v-model="minute">
                <option v-bind:value="null" disabled></option>
                <option v-bind:value="minute && 0 === minute ? minute : 0" :key="0">00</option>
                <option v-bind:value="minute && 1 === minute ? minute : 1" :key="1">01</option>
                <option v-bind:value="minute && 2 === minute ? minute : 2" :key="2">02</option>
                <option v-bind:value="minute && 3 === minute ? minute : 3" :key="3">03</option>
                <option v-bind:value="minute && 4 === minute ? minute : 4" :key="4">04</option>
                <option v-bind:value="minute && 5 === minute ? minute : 5" :key="5">05</option>
                <option v-bind:value="minute && 6 === minute ? minute : 6" :key="6">06</option>
                <option v-bind:value="minute && 7 === minute ? minute : 7" :key="7">07</option>
                <option v-bind:value="minute && 8 === minute ? minute : 8" :key="8">08</option>
                <option v-bind:value="minute && 9 === minute ? minute : 9" :key="9">09</option>
                <option v-bind:value="minute && 10 === minute ? minute : 10" :key="10">10</option>
                <option v-bind:value="minute && 11 === minute ? minute : 11" :key="11">11</option>
                <option v-bind:value="minute && 12 === minute ? minute : 12" :key="12">12</option>
                <option v-bind:value="minute && 13 === minute ? minute : 13" :key="13">13</option>
                <option v-bind:value="minute && 14 === minute ? minute : 14" :key="14">14</option>
                <option v-bind:value="minute && 15 === minute ? minute : 15" :key="15">15</option>
                <option v-bind:value="minute && 16 === minute ? minute : 16" :key="16">16</option>
                <option v-bind:value="minute && 17 === minute ? minute : 17" :key="17">17</option>
                <option v-bind:value="minute && 18 === minute ? minute : 18" :key="18">18</option>
                <option v-bind:value="minute && 19 === minute ? minute : 19" :key="19">19</option>
                <option v-bind:value="minute && 20 === minute ? minute : 20" :key="20">20</option>
                <option v-bind:value="minute && 21 === minute ? minute : 21" :key="21">21</option>
                <option v-bind:value="minute && 22 === minute ? minute : 22" :key="22">22</option>
                <option v-bind:value="minute && 23 === minute ? minute : 23" :key="23">23</option>
                <option v-bind:value="minute && 24 === minute ? minute : 24" :key="24">24</option>
                <option v-bind:value="minute && 25 === minute ? minute : 25" :key="25">25</option>
                <option v-bind:value="minute && 26 === minute ? minute : 26" :key="26">26</option>
                <option v-bind:value="minute && 27 === minute ? minute : 27" :key="27">27</option>
                <option v-bind:value="minute && 28 === minute ? minute : 28" :key="28">28</option>
                <option v-bind:value="minute && 29 === minute ? minute : 29" :key="29">29</option>
                <option v-bind:value="minute && 30 === minute ? minute : 30" :key="30">30</option>
                <option v-bind:value="minute && 31 === minute ? minute : 31" :key="31">31</option>
                <option v-bind:value="minute && 32 === minute ? minute : 32" :key="32">32</option>
                <option v-bind:value="minute && 33 === minute ? minute : 33" :key="33">33</option>
                <option v-bind:value="minute && 34 === minute ? minute : 34" :key="34">34</option>
                <option v-bind:value="minute && 35 === minute ? minute : 35" :key="35">35</option>
                <option v-bind:value="minute && 36 === minute ? minute : 36" :key="36">36</option>
                <option v-bind:value="minute && 37 === minute ? minute : 37" :key="37">37</option>
                <option v-bind:value="minute && 38 === minute ? minute : 38" :key="38">38</option>
                <option v-bind:value="minute && 39 === minute ? minute : 39" :key="39">39</option>
                <option v-bind:value="minute && 40 === minute ? minute : 40" :key="40">40</option>
                <option v-bind:value="minute && 41 === minute ? minute : 41" :key="41">41</option>
                <option v-bind:value="minute && 42 === minute ? minute : 42" :key="42">42</option>
                <option v-bind:value="minute && 43 === minute ? minute : 43" :key="43">43</option>
                <option v-bind:value="minute && 44 === minute ? minute : 44" :key="44">44</option>
                <option v-bind:value="minute && 45 === minute ? minute : 45" :key="45">45</option>
                <option v-bind:value="minute && 46 === minute ? minute : 46" :key="46">46</option>
                <option v-bind:value="minute && 47 === minute ? minute : 47" :key="47">47</option>
                <option v-bind:value="minute && 48 === minute ? minute : 48" :key="48">48</option>
                <option v-bind:value="minute && 49 === minute ? minute : 49" :key="49">49</option>
                <option v-bind:value="minute && 50 === minute ? minute : 50" :key="50">50</option>
                <option v-bind:value="minute && 51 === minute ? minute : 51" :key="51">51</option>
                <option v-bind:value="minute && 52 === minute ? minute : 52" :key="52">52</option>
                <option v-bind:value="minute && 53 === minute ? minute : 53" :key="53">53</option>
                <option v-bind:value="minute && 55 === minute ? minute : 55" :key="55">55</option>
                <option v-bind:value="minute && 56 === minute ? minute : 56" :key="56">56</option>
                <option v-bind:value="minute && 57 === minute ? minute : 57" :key="57">57</option>
                <option v-bind:value="minute && 58 === minute ? minute : 58" :key="58">58</option>
                <option v-bind:value="minute && 59 === minute ? minute : 59" :key="59">59</option>
              </select>
              <div v-if="$v.minute.$invalid">
                <small class="form-text text-danger" v-if="!$v.minute.required"> تاريخ الزيارة مطلوب </small>
              </div>
            </div>
            <div class="col-md-2">
              <select class="form-control" aria-placeholder="الفتره" id="period" data-cy="period" name="period" v-model="period">
                <option v-bind:value="null" disabled></option>
                <option v-bind:value="period && 'am' === period ? period : 'am'" :key="'am'">صباحا</option>
                <option v-bind:value="period && 'pm' === period ? period : 'pm'" :key="'pm'">مساءا</option>
              </select>
              <div v-if="$v.period.$invalid">
                <small class="form-text text-danger" v-if="!$v.period.required"> تاريخ الزيارة مطلوب </small>
              </div>
            </div>
          </div>
          <!-- <date-picker v-model="$v.value" type="datetime"></date-picker>-->

          <div class="form-group row">
            <label class="col-md-2 col-form-label form-control-label" for="visit-clinic">العيادة</label>
            <div class="col-md-10">
              <select class="form-control" id="visit-clinic" data-cy="clinic" name="clinic" v-model="visit.clinic">
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="visit.clinic && clinicOption.id === visit.clinic.id ? visit.clinic : clinicOption"
                  v-for="clinicOption in clinics"
                  :key="clinicOption.id"
                >
                  {{ clinicOption.nameOfClinic }}
                </option>
              </select>
              <div v-if="$v.visit.clinic.$invalid">
                <small class="form-text text-danger" v-if="!$v.visit.clinic.required"> العيادة مطلوب </small>
              </div>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>إلغاء</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.visit.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>حفظ</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./visit-update.component.ts"></script>
