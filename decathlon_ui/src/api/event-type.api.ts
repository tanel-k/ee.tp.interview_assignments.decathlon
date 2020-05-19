import axios, {AxiosResponse} from "axios";

import env from "../env";
import EventTypeDto from "../domain/event-type.dto";
import EventTypeScoreDto from "../domain/event-type-score.dto";

export const findAll = () => axios.get<EventTypeDto[], AxiosResponse<EventTypeDto[]>>(
    `${env.API_ROOT_URL}/decathlon/v1/event-types`
);

export const findScore = (eventTypeName: string, performance: number) => axios.get<EventTypeScoreDto, AxiosResponse<EventTypeScoreDto>>(
    `${env.API_ROOT_URL}/decathlon/v1/event-types/${eventTypeName}/scores?performance=${performance}`
);
