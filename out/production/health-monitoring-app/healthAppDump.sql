--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4
-- Dumped by pg_dump version 15.4

-- Started on 2023-12-19 20:30:27 NST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 16818)
-- Name: doctor_patient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctor_patient (
    doctor_id integer NOT NULL,
    patient_id integer NOT NULL
);


ALTER TABLE public.doctor_patient OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16834)
-- Name: health_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.health_data (
    id integer NOT NULL,
    user_id integer NOT NULL,
    weight numeric(5,2) NOT NULL,
    height numeric(5,2) NOT NULL,
    steps integer NOT NULL,
    heart_rate integer NOT NULL,
    date date NOT NULL,
    sleep_hours integer,
    caloric_intake integer,
    stress_level integer
);


ALTER TABLE public.health_data OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16833)
-- Name: health_data_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.health_data_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.health_data_id_seq OWNER TO postgres;

--
-- TOC entry 3635 (class 0 OID 0)
-- Dependencies: 217
-- Name: health_data_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.health_data_id_seq OWNED BY public.health_data.id;


--
-- TOC entry 220 (class 1259 OID 16846)
-- Name: medicine_reminders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medicine_reminders (
    id integer NOT NULL,
    user_id integer NOT NULL,
    medicine_name character varying(100) NOT NULL,
    dosage character varying(50) NOT NULL,
    schedule character varying(100) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL
);


ALTER TABLE public.medicine_reminders OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16845)
-- Name: medicine_reminders_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medicine_reminders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.medicine_reminders_id_seq OWNER TO postgres;

--
-- TOC entry 3636 (class 0 OID 0)
-- Dependencies: 219
-- Name: medicine_reminders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medicine_reminders_id_seq OWNED BY public.medicine_reminders.id;


--
-- TOC entry 222 (class 1259 OID 16858)
-- Name: recommendations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recommendations (
    id integer NOT NULL,
    user_id integer NOT NULL,
    recommendation_text text NOT NULL,
    date date NOT NULL
);


ALTER TABLE public.recommendations OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16857)
-- Name: recommendations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.recommendations_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.recommendations_id_seq OWNER TO postgres;

--
-- TOC entry 3637 (class 0 OID 0)
-- Dependencies: 221
-- Name: recommendations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.recommendations_id_seq OWNED BY public.recommendations.id;


--
-- TOC entry 215 (class 1259 OID 16810)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    is_doctor boolean NOT NULL,
    medical_license_number character varying(255),
    specialization character varying(255),
    isloggedin boolean
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16809)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 3638 (class 0 OID 0)
-- Dependencies: 214
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 3459 (class 2604 OID 16837)
-- Name: health_data id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.health_data ALTER COLUMN id SET DEFAULT nextval('public.health_data_id_seq'::regclass);


--
-- TOC entry 3460 (class 2604 OID 16849)
-- Name: medicine_reminders id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicine_reminders ALTER COLUMN id SET DEFAULT nextval('public.medicine_reminders_id_seq'::regclass);


--
-- TOC entry 3461 (class 2604 OID 16861)
-- Name: recommendations id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recommendations ALTER COLUMN id SET DEFAULT nextval('public.recommendations_id_seq'::regclass);


--
-- TOC entry 3458 (class 2604 OID 16813)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3623 (class 0 OID 16818)
-- Dependencies: 216
-- Data for Name: doctor_patient; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.doctor_patient (doctor_id, patient_id) FROM stdin;
12	1
12	2
12	3
12	4
12	13
\.


--
-- TOC entry 3625 (class 0 OID 16834)
-- Dependencies: 218
-- Data for Name: health_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.health_data (id, user_id, weight, height, steps, heart_rate, date, sleep_hours, caloric_intake, stress_level) FROM stdin;
425372352	12	22.00	22.00	22	22	2001-09-09	22	22	22
1	1	72.30	178.20	8500	78	2023-01-15	6	2100	5
2	1	71.80	177.50	9000	80	2023-01-16	7	2200	6
3	2	65.50	169.00	9500	75	2023-01-15	8	2000	4
4	2	66.00	170.20	10000	82	2023-01-16	6	2300	7
5	3	82.50	182.50	7500	70	2023-01-15	7	1900	5
6	3	81.00	181.00	8000	72	2023-01-16	8	2100	6
7	4	75.20	175.80	11000	85	2023-01-15	5	2500	8
8	4	74.00	173.50	12000	88	2023-01-16	6	2600	9
6239	13	111.00	122.00	123	123	2023-12-12	12	11111	4
70487	13	123.00	123.00	123	123	2001-12-12	123	123	2
\.


--
-- TOC entry 3627 (class 0 OID 16846)
-- Dependencies: 220
-- Data for Name: medicine_reminders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.medicine_reminders (id, user_id, medicine_name, dosage, schedule, start_date, end_date) FROM stdin;
21013	1	baloban	3 per day	3 days	2222-09-12	2222-10-12
59422	1	colokan	1	3per day	2023-12-12	2024-01-12
26798	13	baloban	2 per day	1 month	2222-09-09	2223-09-09
\.


--
-- TOC entry 3629 (class 0 OID 16858)
-- Dependencies: 222
-- Data for Name: recommendations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recommendations (id, user_id, recommendation_text, date) FROM stdin;
359	12	Your heart rate is lower than the recommended range. Consider increasing your physical activity to improve your cardiovascular health.	2023-12-11
771	12	You're not reaching the recommended daily step count. Try to incorporate more walking or other physical activities into your daily routine.	2023-12-11
957	1	You're not reaching the recommended daily step count. Try to incorporate more walking or other physical activities into your daily routine.	2023-12-11
765	1	You're not reaching the recommended daily step count. Try to incorporate more walking or other physical activities into your daily routine.	2023-12-11
613	2	You're not reaching the recommended daily step count. Try to incorporate more walking or other physical activities into your daily routine.	2023-12-11
443	3	You're not reaching the recommended daily step count. Try to incorporate more walking or other physical activities into your daily routine.	2023-12-11
271	3	You're not reaching the recommended daily step count. Try to incorporate more walking or other physical activities into your daily routine.	2023-12-11
28773	13	Your heart rate is higher than the recommended range. Consider consulting with a healthcare professional to assess your cardiovascular health.	2023-12-12
54960	13	You're not reaching the recommended daily step count. Try to incorporate more walking or other physical activities into your daily routine.	2023-12-12
98839	13	Your heart rate is higher than the recommended range. Consider consulting with a healthcare professional to assess your cardiovascular health.	2023-12-12
41899	13	You're not reaching the recommended daily step count. Try to incorporate more walking or other physical activities into your daily routine.	2023-12-12
55594	13	Your sleep hours are outside the recommended range. Ensure you are getting sufficient sleep for optimal health.	2023-12-12
2616	13	Your caloric intake is outside the recommended range. Consider consulting with a nutritionist for personalized dietary advice.	2023-12-12
\.


--
-- TOC entry 3622 (class 0 OID 16810)
-- Dependencies: 215
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, first_name, last_name, email, password, is_doctor, medical_license_number, specialization, isloggedin) FROM stdin;
1	John	Doe	john.doe@example.com	password123	f	\N	\N	f
3	Alice	Smith	alice.smith@example.com	pass789	f	\N	\N	f
4	Bob	Johnson	bob.johnson@example.com	secret321	f	\N	\N	f
6	Michael	Brown	michael.brown@example.com	securepass	f	\N	\N	f
8	David	Clark	david.clark@example.com	pass1234	f	\N	\N	f
9	Olivia	Jones	olivia.jones@example.com	userpass	f	\N	\N	f
2	Jane	Doe	jane.doe@example.com	password456	t	DoctorLicense123	Cardiology	f
5	Eva	Williams	eva.williams@example.com	letmein	t	DoctorLicense456	Dermatology	f
7	Emily	Taylor	emily.taylor@example.com	mypassword	t	DoctorLicense789	Pediatrics	f
10	Jacob	Moore	jacob.moore@example.com	mypass123	t	DoctorLicense101	Orthopedics	f
11	asdasd	asdasd	asdasd	$2a$10$GidbrGvbTt9.8x37i8bxNOBx7VaIFzqTQrwx6gwFeJmbUzuy5qJfi	f	\N	\N	f
12	qweqwe	qweqwe	qweqwe	$2a$10$CaZIFJQZNtXg/s90GIgnue.WwOt11uM84jl3BZdGoohSl6JU9GFjq	t	123123213	Super-Doctor	f
13	asd	asd	asd	$2a$10$uD3fCNUt2aJYYuI1hIjA1ukl1PzNLL2Agq64SWHatcAj5APgjJ1CS	f	\N	\N	t
\.


--
-- TOC entry 3639 (class 0 OID 0)
-- Dependencies: 217
-- Name: health_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.health_data_id_seq', 1, false);


--
-- TOC entry 3640 (class 0 OID 0)
-- Dependencies: 219
-- Name: medicine_reminders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medicine_reminders_id_seq', 1, false);


--
-- TOC entry 3641 (class 0 OID 0)
-- Dependencies: 221
-- Name: recommendations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recommendations_id_seq', 1, false);


--
-- TOC entry 3642 (class 0 OID 0)
-- Dependencies: 214
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- TOC entry 3467 (class 2606 OID 16822)
-- Name: doctor_patient doctor_patient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor_patient
    ADD CONSTRAINT doctor_patient_pkey PRIMARY KEY (doctor_id, patient_id);


--
-- TOC entry 3469 (class 2606 OID 16839)
-- Name: health_data health_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.health_data
    ADD CONSTRAINT health_data_pkey PRIMARY KEY (id);


--
-- TOC entry 3471 (class 2606 OID 16851)
-- Name: medicine_reminders medicine_reminders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicine_reminders
    ADD CONSTRAINT medicine_reminders_pkey PRIMARY KEY (id);


--
-- TOC entry 3473 (class 2606 OID 16865)
-- Name: recommendations recommendations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recommendations
    ADD CONSTRAINT recommendations_pkey PRIMARY KEY (id);


--
-- TOC entry 3463 (class 2606 OID 16817)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 3465 (class 2606 OID 16815)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3474 (class 2606 OID 16823)
-- Name: doctor_patient doctor_patient_doctor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor_patient
    ADD CONSTRAINT doctor_patient_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.users(id);


--
-- TOC entry 3475 (class 2606 OID 16828)
-- Name: doctor_patient doctor_patient_patient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor_patient
    ADD CONSTRAINT doctor_patient_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.users(id);


--
-- TOC entry 3476 (class 2606 OID 16840)
-- Name: health_data health_data_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.health_data
    ADD CONSTRAINT health_data_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3477 (class 2606 OID 16852)
-- Name: medicine_reminders medicine_reminders_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicine_reminders
    ADD CONSTRAINT medicine_reminders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3478 (class 2606 OID 16866)
-- Name: recommendations recommendations_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recommendations
    ADD CONSTRAINT recommendations_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


-- Completed on 2023-12-19 20:30:27 NST

--
-- PostgreSQL database dump complete
--

