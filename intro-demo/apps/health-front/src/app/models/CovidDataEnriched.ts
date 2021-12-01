export interface CovidDataEnriched {
    countryCode: string;
    countryName: string;
    cumulativePositive: number;
    cumulativeDeceased: number;
    cumulativeRecovered: number;
    firstDose: number;
    secondDose: number;
    yearWeekISO: string;
    vaccine: string;
    flagImageURL: string;
    population: number;
}
