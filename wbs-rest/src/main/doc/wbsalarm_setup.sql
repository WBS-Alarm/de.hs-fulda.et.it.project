--
-- PostgreSQL database dump
--

-- Dumped from database version 10.5 (Ubuntu 10.5-0ubuntu0.18.04)
-- Dumped by pg_dump version 10.5 (Ubuntu 10.5-0ubuntu0.18.04)

-- Started on 2018-08-25 15:54:10 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 13052)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3040 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 18910)
-- Name: benutzer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.benutzer (
    id bigint NOT NULL,
    einkaeufer boolean,
    mail character varying(254),
    password character varying(60),
    name character varying(60),
    traeger_id bigint
);


ALTER TABLE public.benutzer OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 18908)
-- Name: benutzer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.benutzer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.benutzer_id_seq OWNER TO postgres;

--
-- TOC entry 3041 (class 0 OID 0)
-- Dependencies: 196
-- Name: benutzer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.benutzer_id_seq OWNED BY public.benutzer.id;


--
-- TOC entry 199 (class 1259 OID 18918)
-- Name: bestaende; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bestaende (
    id bigint NOT NULL,
    anzahl bigint,
    groesse_id bigint,
    kategorie_id bigint,
    zielort_id bigint
);


ALTER TABLE public.bestaende OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 18916)
-- Name: bestaende_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bestaende_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bestaende_id_seq OWNER TO postgres;

--
-- TOC entry 3042 (class 0 OID 0)
-- Dependencies: 198
-- Name: bestaende_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bestaende_id_seq OWNED BY public.bestaende.id;


--
-- TOC entry 201 (class 1259 OID 18926)
-- Name: groessen; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.groessen (
    id bigint NOT NULL,
    name character varying(20)
);


ALTER TABLE public.groessen OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 18924)
-- Name: groessen_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.groessen_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.groessen_id_seq OWNER TO postgres;

--
-- TOC entry 3043 (class 0 OID 0)
-- Dependencies: 200
-- Name: groessen_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.groessen_id_seq OWNED BY public.groessen.id;


--
-- TOC entry 203 (class 1259 OID 18934)
-- Name: kategorien; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.kategorien (
    id bigint NOT NULL,
    name character varying(40),
    traeger_id bigint
);


ALTER TABLE public.kategorien OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 18940)
-- Name: kategorien_groessen; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.kategorien_groessen (
    groesse_id bigint NOT NULL,
    kategorie_id bigint NOT NULL
);


ALTER TABLE public.kategorien_groessen OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 18932)
-- Name: kategorien_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.kategorien_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.kategorien_id_seq OWNER TO postgres;

--
-- TOC entry 3044 (class 0 OID 0)
-- Dependencies: 202
-- Name: kategorien_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.kategorien_id_seq OWNED BY public.kategorien.id;


--
-- TOC entry 206 (class 1259 OID 18945)
-- Name: kontakte; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.kontakte (
    id bigint NOT NULL,
    mail character varying(254),
    name character varying(60),
    zielort_id bigint
);


ALTER TABLE public.kontakte OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 18943)
-- Name: kontakte_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.kontakte_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.kontakte_id_seq OWNER TO postgres;

--
-- TOC entry 3045 (class 0 OID 0)
-- Dependencies: 205
-- Name: kontakte_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.kontakte_id_seq OWNED BY public.kontakte.id;


--
-- TOC entry 208 (class 1259 OID 18953)
-- Name: positionen; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.positionen (
    id bigint NOT NULL,
    anzahl bigint,
    groesse_id bigint,
    kategorie_id bigint,
    transaktion_id bigint
);


ALTER TABLE public.positionen OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 18951)
-- Name: positionen_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.positionen_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.positionen_id_seq OWNER TO postgres;

--
-- TOC entry 3046 (class 0 OID 0)
-- Dependencies: 207
-- Name: positionen_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.positionen_id_seq OWNED BY public.positionen.id;


--
-- TOC entry 210 (class 1259 OID 18961)
-- Name: traeger; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.traeger (
    id bigint NOT NULL,
    name character varying(60)
);


ALTER TABLE public.traeger OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 18959)
-- Name: traeger_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.traeger_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.traeger_id_seq OWNER TO postgres;

--
-- TOC entry 3047 (class 0 OID 0)
-- Dependencies: 209
-- Name: traeger_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.traeger_id_seq OWNED BY public.traeger.id;


--
-- TOC entry 212 (class 1259 OID 18969)
-- Name: transaktionen; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transaktionen (
    id bigint NOT NULL,
    date timestamp without time zone,
    benutzer_id bigint,
    zielort_id bigint
);


ALTER TABLE public.transaktionen OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 18967)
-- Name: transaktionen_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transaktionen_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transaktionen_id_seq OWNER TO postgres;

--
-- TOC entry 3048 (class 0 OID 0)
-- Dependencies: 211
-- Name: transaktionen_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transaktionen_id_seq OWNED BY public.transaktionen.id;


--
-- TOC entry 214 (class 1259 OID 18977)
-- Name: zielorte; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zielorte (
    id bigint NOT NULL,
    name character varying(60),
    traeger_id bigint
);


ALTER TABLE public.zielorte OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 18975)
-- Name: zielorte_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.zielorte_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.zielorte_id_seq OWNER TO postgres;

--
-- TOC entry 3049 (class 0 OID 0)
-- Dependencies: 213
-- Name: zielorte_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.zielorte_id_seq OWNED BY public.zielorte.id;


--
-- TOC entry 2850 (class 2604 OID 18913)
-- Name: benutzer id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.benutzer ALTER COLUMN id SET DEFAULT nextval('public.benutzer_id_seq'::regclass);


--
-- TOC entry 2851 (class 2604 OID 18921)
-- Name: bestaende id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bestaende ALTER COLUMN id SET DEFAULT nextval('public.bestaende_id_seq'::regclass);


--
-- TOC entry 2852 (class 2604 OID 18929)
-- Name: groessen id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groessen ALTER COLUMN id SET DEFAULT nextval('public.groessen_id_seq'::regclass);


--
-- TOC entry 2853 (class 2604 OID 18937)
-- Name: kategorien id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kategorien ALTER COLUMN id SET DEFAULT nextval('public.kategorien_id_seq'::regclass);


--
-- TOC entry 2854 (class 2604 OID 18948)
-- Name: kontakte id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kontakte ALTER COLUMN id SET DEFAULT nextval('public.kontakte_id_seq'::regclass);


--
-- TOC entry 2855 (class 2604 OID 18956)
-- Name: positionen id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.positionen ALTER COLUMN id SET DEFAULT nextval('public.positionen_id_seq'::regclass);


--
-- TOC entry 2856 (class 2604 OID 18964)
-- Name: traeger id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.traeger ALTER COLUMN id SET DEFAULT nextval('public.traeger_id_seq'::regclass);


--
-- TOC entry 2857 (class 2604 OID 18972)
-- Name: transaktionen id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaktionen ALTER COLUMN id SET DEFAULT nextval('public.transaktionen_id_seq'::regclass);


--
-- TOC entry 2858 (class 2604 OID 18980)
-- Name: zielorte id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zielorte ALTER COLUMN id SET DEFAULT nextval('public.zielorte_id_seq'::regclass);


--
-- TOC entry 3015 (class 0 OID 18910)
-- Dependencies: 197
-- Data for Name: benutzer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.benutzer (id, einkaeufer, mail, password, name, traeger_id) FROM stdin;
1	\N	\N	$2a$12$n8VYyQqx5V5a.j86aLkytOChTq.n470ZahV2MihvkwGyCLbOinh4q	johnny	\N
\.


--
-- TOC entry 3017 (class 0 OID 18918)
-- Dependencies: 199
-- Data for Name: bestaende; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bestaende (id, anzahl, groesse_id, kategorie_id, zielort_id) FROM stdin;
\.


--
-- TOC entry 3019 (class 0 OID 18926)
-- Dependencies: 201
-- Data for Name: groessen; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.groessen (id, name) FROM stdin;
\.


--
-- TOC entry 3021 (class 0 OID 18934)
-- Dependencies: 203
-- Data for Name: kategorien; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.kategorien (id, name, traeger_id) FROM stdin;
\.


--
-- TOC entry 3022 (class 0 OID 18940)
-- Dependencies: 204
-- Data for Name: kategorien_groessen; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.kategorien_groessen (groesse_id, kategorie_id) FROM stdin;
\.


--
-- TOC entry 3024 (class 0 OID 18945)
-- Dependencies: 206
-- Data for Name: kontakte; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.kontakte (id, mail, name, zielort_id) FROM stdin;
\.


--
-- TOC entry 3026 (class 0 OID 18953)
-- Dependencies: 208
-- Data for Name: positionen; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.positionen (id, anzahl, groesse_id, kategorie_id, transaktion_id) FROM stdin;
\.


--
-- TOC entry 3028 (class 0 OID 18961)
-- Dependencies: 210
-- Data for Name: traeger; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.traeger (id, name) FROM stdin;
\.


--
-- TOC entry 3030 (class 0 OID 18969)
-- Dependencies: 212
-- Data for Name: transaktionen; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transaktionen (id, date, benutzer_id, zielort_id) FROM stdin;
\.


--
-- TOC entry 3032 (class 0 OID 18977)
-- Dependencies: 214
-- Data for Name: zielorte; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.zielorte (id, name, traeger_id) FROM stdin;
\.


--
-- TOC entry 3050 (class 0 OID 0)
-- Dependencies: 196
-- Name: benutzer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.benutzer_id_seq', 1, true);


--
-- TOC entry 3051 (class 0 OID 0)
-- Dependencies: 198
-- Name: bestaende_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bestaende_id_seq', 1, false);


--
-- TOC entry 3052 (class 0 OID 0)
-- Dependencies: 200
-- Name: groessen_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.groessen_id_seq', 1, false);


--
-- TOC entry 3053 (class 0 OID 0)
-- Dependencies: 202
-- Name: kategorien_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.kategorien_id_seq', 1, false);


--
-- TOC entry 3054 (class 0 OID 0)
-- Dependencies: 205
-- Name: kontakte_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.kontakte_id_seq', 1, false);


--
-- TOC entry 3055 (class 0 OID 0)
-- Dependencies: 207
-- Name: positionen_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.positionen_id_seq', 1, false);


--
-- TOC entry 3056 (class 0 OID 0)
-- Dependencies: 209
-- Name: traeger_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.traeger_id_seq', 1, false);


--
-- TOC entry 3057 (class 0 OID 0)
-- Dependencies: 211
-- Name: transaktionen_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transaktionen_id_seq', 1, false);


--
-- TOC entry 3058 (class 0 OID 0)
-- Dependencies: 213
-- Name: zielorte_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.zielorte_id_seq', 1, false);


--
-- TOC entry 2860 (class 2606 OID 18915)
-- Name: benutzer benutzer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.benutzer
    ADD CONSTRAINT benutzer_pkey PRIMARY KEY (id);


--
-- TOC entry 2862 (class 2606 OID 18923)
-- Name: bestaende bestaende_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bestaende
    ADD CONSTRAINT bestaende_pkey PRIMARY KEY (id);


--
-- TOC entry 2864 (class 2606 OID 18931)
-- Name: groessen groessen_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groessen
    ADD CONSTRAINT groessen_pkey PRIMARY KEY (id);


--
-- TOC entry 2866 (class 2606 OID 18939)
-- Name: kategorien kategorien_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kategorien
    ADD CONSTRAINT kategorien_pkey PRIMARY KEY (id);


--
-- TOC entry 2868 (class 2606 OID 18950)
-- Name: kontakte kontakte_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kontakte
    ADD CONSTRAINT kontakte_pkey PRIMARY KEY (id);


--
-- TOC entry 2870 (class 2606 OID 18958)
-- Name: positionen positionen_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.positionen
    ADD CONSTRAINT positionen_pkey PRIMARY KEY (id);


--
-- TOC entry 2872 (class 2606 OID 18966)
-- Name: traeger traeger_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.traeger
    ADD CONSTRAINT traeger_pkey PRIMARY KEY (id);


--
-- TOC entry 2874 (class 2606 OID 18974)
-- Name: transaktionen transaktionen_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaktionen
    ADD CONSTRAINT transaktionen_pkey PRIMARY KEY (id);


--
-- TOC entry 2876 (class 2606 OID 18982)
-- Name: zielorte zielorte_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zielorte
    ADD CONSTRAINT zielorte_pkey PRIMARY KEY (id);


--
-- TOC entry 2889 (class 2606 OID 19043)
-- Name: positionen fk1vym23x3140ttoc60mgf6nass; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.positionen
    ADD CONSTRAINT fk1vym23x3140ttoc60mgf6nass FOREIGN KEY (transaktion_id) REFERENCES public.transaktionen(id);


--
-- TOC entry 2890 (class 2606 OID 19048)
-- Name: transaktionen fk3a7vkvw0y9e7b8mnh1ow81s3s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaktionen
    ADD CONSTRAINT fk3a7vkvw0y9e7b8mnh1ow81s3s FOREIGN KEY (benutzer_id) REFERENCES public.benutzer(id);


--
-- TOC entry 2882 (class 2606 OID 19008)
-- Name: kategorien fk4ub5f2dwfwgwgu3wysuh47ngp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kategorien
    ADD CONSTRAINT fk4ub5f2dwfwgwgu3wysuh47ngp FOREIGN KEY (traeger_id) REFERENCES public.traeger(id);


--
-- TOC entry 2884 (class 2606 OID 19018)
-- Name: kategorien_groessen fk6ao15970ey9vwmr0oawe2fjpj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kategorien_groessen
    ADD CONSTRAINT fk6ao15970ey9vwmr0oawe2fjpj FOREIGN KEY (groesse_id) REFERENCES public.groessen(id);


--
-- TOC entry 2886 (class 2606 OID 19028)
-- Name: kontakte fkcdmavmerdkxpm0drh8nijy0ej; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kontakte
    ADD CONSTRAINT fkcdmavmerdkxpm0drh8nijy0ej FOREIGN KEY (id) REFERENCES public.kontakte(id);


--
-- TOC entry 2881 (class 2606 OID 19003)
-- Name: bestaende fkd0mulshe62dfwkn2l4kcfh2su; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bestaende
    ADD CONSTRAINT fkd0mulshe62dfwkn2l4kcfh2su FOREIGN KEY (id) REFERENCES public.bestaende(id);


--
-- TOC entry 2887 (class 2606 OID 19033)
-- Name: positionen fkeb5lhph3bs1ys2l1dqree62ig; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.positionen
    ADD CONSTRAINT fkeb5lhph3bs1ys2l1dqree62ig FOREIGN KEY (groesse_id) REFERENCES public.groessen(id);


--
-- TOC entry 2892 (class 2606 OID 19058)
-- Name: zielorte fkers56pvudy92efwhvu2i4yc64; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zielorte
    ADD CONSTRAINT fkers56pvudy92efwhvu2i4yc64 FOREIGN KEY (traeger_id) REFERENCES public.traeger(id);


--
-- TOC entry 2888 (class 2606 OID 19038)
-- Name: positionen fkg0uu574a0brqjk8lpaypirvht; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.positionen
    ADD CONSTRAINT fkg0uu574a0brqjk8lpaypirvht FOREIGN KEY (kategorie_id) REFERENCES public.kategorien(id);


--
-- TOC entry 2877 (class 2606 OID 18983)
-- Name: benutzer fkjhm5boye9h74jmu94np0a78my; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.benutzer
    ADD CONSTRAINT fkjhm5boye9h74jmu94np0a78my FOREIGN KEY (traeger_id) REFERENCES public.traeger(id);


--
-- TOC entry 2878 (class 2606 OID 18988)
-- Name: bestaende fkoeagcynvwgh9poes3pncyg75s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bestaende
    ADD CONSTRAINT fkoeagcynvwgh9poes3pncyg75s FOREIGN KEY (groesse_id) REFERENCES public.groessen(id);


--
-- TOC entry 2880 (class 2606 OID 18998)
-- Name: bestaende fkot6w0gfahi7olp00licxpi6uo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bestaende
    ADD CONSTRAINT fkot6w0gfahi7olp00licxpi6uo FOREIGN KEY (zielort_id) REFERENCES public.zielorte(id);


--
-- TOC entry 2885 (class 2606 OID 19023)
-- Name: kontakte fkpc2grd692ituvmyt8b85u3ouv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kontakte
    ADD CONSTRAINT fkpc2grd692ituvmyt8b85u3ouv FOREIGN KEY (zielort_id) REFERENCES public.zielorte(id);


--
-- TOC entry 2879 (class 2606 OID 18993)
-- Name: bestaende fkphk5o9odn1iujcmy69afu763j; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bestaende
    ADD CONSTRAINT fkphk5o9odn1iujcmy69afu763j FOREIGN KEY (kategorie_id) REFERENCES public.kategorien(id);


--
-- TOC entry 2883 (class 2606 OID 19013)
-- Name: kategorien_groessen fkpq7hcv0rn9ra6b3e2l4qvu5s6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kategorien_groessen
    ADD CONSTRAINT fkpq7hcv0rn9ra6b3e2l4qvu5s6 FOREIGN KEY (kategorie_id) REFERENCES public.kategorien(id);


--
-- TOC entry 2891 (class 2606 OID 19053)
-- Name: transaktionen fksfafsoiysenjvdyrr0hmm6jad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaktionen
    ADD CONSTRAINT fksfafsoiysenjvdyrr0hmm6jad FOREIGN KEY (zielort_id) REFERENCES public.zielorte(id);


-- Completed on 2018-08-25 15:54:10 CEST

--
-- PostgreSQL database dump complete
--

