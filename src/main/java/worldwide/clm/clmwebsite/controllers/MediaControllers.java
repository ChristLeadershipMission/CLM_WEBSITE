package worldwide.clm.clmwebsite.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.data.models.Audio;
import worldwide.clm.clmwebsite.data.models.Media;
import worldwide.clm.clmwebsite.data.models.Video;
import worldwide.clm.clmwebsite.dto.request.AudioCreationRequest;
import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;
import worldwide.clm.clmwebsite.dto.request.VideoCreationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;
import worldwide.clm.clmwebsite.exception.MediaProcessingException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.mediaServices.MediaService;

@RestController
@AllArgsConstructor
@RequestMapping("/clmWebsite/api/v1/media/")
public class MediaControllers {
    private final MediaService mediaService;

    @Operation(
            summary = "Create Video post",
            description = "API for creating video post."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Video created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
    )
    @PostMapping("video")
    public ResponseEntity<Video> createVideo(
            @RequestBody VideoCreationRequest videoCreationRequest
    ) throws MediaProcessingException {
        return new ResponseEntity<>(mediaService.createVideo(videoCreationRequest), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Create Audio post",
            description = "API for creating audio post."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Audio created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
    )
    @PostMapping("audio")
    public ResponseEntity<Audio> createAudio(
            @RequestBody AudioCreationRequest audioCreationRequest
    ) throws MediaProcessingException {
        return new ResponseEntity<>(mediaService.createAudio(audioCreationRequest), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Retrieve Media Data",
            description = "API for retrieving media data."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Media retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
    )
    @GetMapping("retrieve-media-data")
    public ResponseEntity<Media> retrieveMediaData() {
        return new ResponseEntity<>(mediaService.retrieveMediaData(), HttpStatus.OK);
    }
}
