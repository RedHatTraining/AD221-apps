import { getRESTClient, ServiceName } from "./API";
import { CovidDataEnriched } from "../models/CovidDataEnriched";

const API = getRESTClient(ServiceName.BACKEND);

export function getCovidDataEnriched(): Promise<CovidDataEnriched[]> {
    return API.url("/covid-data-enriched")
        .get()
        .json<CovidDataEnriched[]>()
}

